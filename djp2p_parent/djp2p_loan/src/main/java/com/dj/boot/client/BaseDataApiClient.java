package com.dj.boot.client;


import com.dj.boot.base.BaseDataApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-service")
public interface BaseDataApiClient extends BaseDataApi {
}
