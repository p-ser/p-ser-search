package com.pser.search.api;

import com.pser.search.application.ReservationService;
import com.pser.search.common.response.ApiResponse;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.ReservationSearchRequest;
import com.pser.search.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationApi {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<SearchSlice<ReservationResponse>>> search(ReservationSearchRequest request,
                                                                                @PageableDefault
                                                                                Pageable pageable) {
        SearchSlice<ReservationResponse> result = reservationService.search(request, pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getById(@PathVariable long reservationId) {
        ReservationResponse result = reservationService.getById(reservationId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
