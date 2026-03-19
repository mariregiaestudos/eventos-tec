package com.eventostec.api.controller;

import com.eventostec.api.Domain.coupon.Coupon;
import com.eventostec.api.Domain.coupon.CouponRequestDTO;
import com.eventostec.api.Services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> create(@PathVariable UUID eventId, @RequestBody CouponRequestDTO request){
        Coupon coupons = this.couponService.create(eventId, request);
        return ResponseEntity.ok(coupons);
    }
}
