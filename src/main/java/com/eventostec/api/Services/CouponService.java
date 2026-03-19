package com.eventostec.api.Services;

import com.eventostec.api.Domain.coupon.Coupon;
import com.eventostec.api.Domain.coupon.CouponRequestDTO;
import com.eventostec.api.Domain.event.Event;
import com.eventostec.api.Repositories.CouponRepository;
import com.eventostec.api.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public CouponService(CouponRepository couponRepository,  EventRepository eventRepository) {
        this.couponRepository = couponRepository;
        this.eventRepository = eventRepository;
    }

    public Coupon create(UUID eventId, CouponRequestDTO request){
        Coupon coupon = new Coupon();
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        coupon.setDiscount(request.discount());
        coupon.setValid(new Date(request.valid()));
        coupon.setCode(request.code());
        coupon.setEvent(event);
        return couponRepository.save(coupon);
    }
}
