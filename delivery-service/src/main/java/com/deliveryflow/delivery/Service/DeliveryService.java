package com.deliveryflow.delivery.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.deliveryflow.delivery.Deliveries.CreateDeliveryRequest;
import com.deliveryflow.delivery.Deliveries.CreateDeliveryResponse;
import com.deliveryflow.delivery.Deliveries.DeliveriesEntity;
import com.deliveryflow.delivery.Deliveries.DeliveryMapper;
import com.deliveryflow.delivery.Exception.DestinationNotSupportedException;
import com.deliveryflow.delivery.Exception.HubConfigurationException;
import com.deliveryflow.delivery.Exception.NotFoundDeliveryOrderByIdException;
import com.deliveryflow.delivery.Exception.UnCorrectDeliveriesInfoException;
import com.deliveryflow.delivery.Hub.HubEntity;
import com.deliveryflow.delivery.Hub.HubMapper;
import com.deliveryflow.delivery.Hub.InfoAboutHub;
import com.deliveryflow.delivery.Order.InfoAboutDeliveryOrder;
import com.deliveryflow.delivery.Order.OrderDeliveriesResponse;
import com.deliveryflow.delivery.Order.OrderMapper;
import com.deliveryflow.delivery.Repository.DeliveriesRepository;
import com.deliveryflow.delivery.Repository.HubRepository;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveriesRepository DR;
    private final HubRepository HR;

    public CreateDeliveryResponse CreateDeliveryOrder(CreateDeliveryRequest request) {
        var existing = DR.findBySellerOrderId(request.sellerOrderId());
        if (existing.isPresent()) {
            return repeatedRequest(existing.get(), request);
        }

        HubEntity mainHub = findMainHub();
        HubEntity destinationHub = HR.findByServedCity(
            request.deliveryAddress().countryCode(), request.deliveryAddress().city()
        ).orElseThrow(() -> new DestinationNotSupportedException(request.deliveryAddress().city()));

        DeliveriesEntity entity = DeliveryMapper.RequestTransformInEntity(request);
        entity.setOriginHubId(mainHub.getId());
        entity.setDestinationHubId(destinationHub.getId());

        try {
            DeliveriesEntity saved = DR.saveAndFlush(entity);
            return new CreateDeliveryResponse(DeliveryMapper.EntityTransformInResponse(saved), false);
        } catch (DataIntegrityViolationException ex) {
            // The unique seller_order_id constraint also protects concurrent requests.
            return DR.findBySellerOrderId(request.sellerOrderId())
                .map(saved -> repeatedRequest(saved, request))
                .orElseThrow(() -> ex);
        }
    }

    private CreateDeliveryResponse repeatedRequest(DeliveriesEntity saved, CreateDeliveryRequest request) {
        if (!saved.equals(DeliveryMapper.RequestTransformInEntity(request))) {
            throw new UnCorrectDeliveriesInfoException(
                "Delivery with this sellerOrderId already exists with different request data."
            );
        }
        return new CreateDeliveryResponse(DeliveryMapper.EntityTransformInResponse(saved), true);
    }

    private HubEntity findMainHub() {
        return HR.findByType("MAIN_MOSCOW")
            .orElseThrow(() -> new HubConfigurationException("Главный московский пункт не настроен."));
    }

    public InfoAboutDeliveryOrder showPartOrder(UUID deliveryId){
        DeliveriesEntity entity=DR.findById(deliveryId).
        orElseThrow(()->new NotFoundDeliveryOrderByIdException("Delivery Order with this id not found"));
        HubEntity mainHub = findMainHub();
        HubEntity destinationHub = HR.findByServedCity(
            entity.getDeliveryCountryCode(), entity.getDeliveryCity()
        ).orElseThrow(() -> new HubConfigurationException(
            "Для сохранённой доставки отсутствует пункт города получателя."
        ));

        return OrderMapper.toDto(entity, mainHub, destinationHub);
    }

    public List<InfoAboutHub> showAllHubs(){
        return HR.findAll().stream().map(HubMapper::toDto).toList();
    }

    public OrderDeliveriesResponse showOrder(Long orderId) {
        var items = DR.findAllByOrderIdOrderBySellerOrderIdAsc(orderId).stream()
            .map(DeliveryMapper::EntityTransformInResponse)
            .toList();
        return new OrderDeliveriesResponse(orderId, items);
    }
}
