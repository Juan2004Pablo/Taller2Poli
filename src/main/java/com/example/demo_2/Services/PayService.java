package com.example.demo_2.Services;

import com.example.demo_2.Models.Entities.Pay;

public interface PayService {
    Pay createPay(Long idOrder);
    Pay updatePay(Pay pay);
    void updatePendingPays();
}
