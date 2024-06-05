package com.pser.search.dto.request;


import com.pser.search.domain.HotelCategoryEnum;
import com.pser.search.dto.SearchQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ReservationSearchRequest extends SearchQuery {
    private Double userLatitude;

    private Double userLongitude;

    private Integer minDistance;

    private Integer maxDistance;

    private Long userId;

    private String name;

    private HotelCategoryEnum category;

    private String province;

    private String city;

    private String district;

    private String detailedAddress;

    private Boolean parkingLot;

    private Boolean wifi;

    private Boolean barbecue;

    private Boolean sauna;

    private Boolean swimmingPool;

    private Boolean restaurant;

    private Boolean roofTop;

    private Boolean fitness;

    private Boolean dryer;

    private Boolean breakfast;

    private Boolean smokingArea;

    private Boolean allTimeDesk;

    private Boolean luggageStorage;

    private Boolean snackBar;

    private Boolean petFriendly;

    private Integer people;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAtAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAtBefore;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAtAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAtBefore;


    @Builder
    public ReservationSearchRequest(String keyword, Double scoreAfter, Long idAfter, LocalDateTime createdAfter,
                                    LocalDateTime createdBefore,
                                    LocalDateTime updatedAfter, LocalDateTime updatedBefore, String name,
                                    HotelCategoryEnum category, String province, String city, String district,
                                    String detailedAddress, Boolean parkingLot, Boolean wifi, Boolean barbecue,
                                    Boolean sauna,
                                    Boolean swimmingPool, Boolean restaurant, Boolean roofTop, Boolean fitness,
                                    Boolean dryer,
                                    Boolean breakfast, Boolean smokingArea, Boolean allTimeDesk, Boolean luggageStorage,
                                    Boolean snackBar, Boolean petFriendly, Integer people, LocalDate startAt,
                                    LocalDate startAtAfter, LocalDate startAtBefore, LocalDate endAt,
                                    LocalDate endAtAfter,
                                    LocalDate endAtBefore) {
        super(keyword, scoreAfter, idAfter, createdAfter, createdBefore, updatedAfter, updatedBefore);
        this.name = name;
        this.category = category;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailedAddress = detailedAddress;
        this.parkingLot = parkingLot;
        this.wifi = wifi;
        this.barbecue = barbecue;
        this.sauna = sauna;
        this.swimmingPool = swimmingPool;
        this.restaurant = restaurant;
        this.roofTop = roofTop;
        this.fitness = fitness;
        this.dryer = dryer;
        this.breakfast = breakfast;
        this.smokingArea = smokingArea;
        this.allTimeDesk = allTimeDesk;
        this.luggageStorage = luggageStorage;
        this.snackBar = snackBar;
        this.petFriendly = petFriendly;
        this.people = people;
        this.startAt = startAt;
        this.startAtAfter = startAtAfter;
        this.startAtBefore = startAtBefore;
        this.endAt = endAt;
        this.endAtAfter = endAtAfter;
        this.endAtBefore = endAtBefore;
    }
}
