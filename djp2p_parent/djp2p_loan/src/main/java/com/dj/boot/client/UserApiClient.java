package com.dj.boot.client;


import com.dj.boot.base.BaseDataApi;
import com.dj.boot.user.UserApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "user-service")
public interface UserApiClient extends UserApi {
}
