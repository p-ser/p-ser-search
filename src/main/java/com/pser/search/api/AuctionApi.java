package com.pser.search.api;

import com.pser.search.application.AuctionService;
import com.pser.search.common.response.ApiResponse;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.AuctionSearchRequest;
import com.pser.search.dto.response.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionApi {
    private final AuctionService auctionService;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchSlice<AuctionResponse>>> search(AuctionSearchRequest request,
                                                                            @PageableDefault
                                                                            Pageable pageable) {
        SearchSlice<AuctionResponse> result = auctionService.search(request, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionResponse>> getById(@PathVariable long auctionId) {
        AuctionResponse result = auctionService.getById(auctionId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
