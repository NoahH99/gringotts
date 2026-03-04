package com.noahhendrickson.ledger.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;

public interface EarningService {

    long tryAward(EarningEvent event);
}
