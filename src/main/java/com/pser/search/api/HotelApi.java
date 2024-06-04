package com.pser.search.api;

import com.pser.search.application.HotelService;
import com.pser.search.common.response.ApiResponse;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.HotelSearchRequest;
import com.pser.search.dto.response.HotelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelApi {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchSlice<HotelResponse>>> search(HotelSearchRequest request, @PageableDefault
    Pageable pageable) {
        SearchSlice<HotelResponse> result = hotelService.search(request, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<HotelResponse>> getById(@PathVariable long hotelId) {
        HotelResponse result = hotelService.getById(hotelId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
