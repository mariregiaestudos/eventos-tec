package com.eventostec.api.Domain.coupon;

import java.util.Date;

public record CouponRequestDTO (String code, Integer discount, Long valid){
}
