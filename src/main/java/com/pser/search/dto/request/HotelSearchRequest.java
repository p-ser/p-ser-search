package com.pser.search.dto.request;


import com.pser.search.domain.HotelCategoryEnum;
import com.pser.search.dto.SearchQuery;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelSearchRequest extends SearchQuery {
    private Double userLatitude;

    private Double userLongitude;

    private Integer minDistance;

    private Integer maxDistance;

    private Long hotelOwnerId;

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


    public HotelSearchRequest(String keyword, Double scoreAfter, Long idAfter, LocalDateTime createdAfter,
                              LocalDateTime createdBefore, LocalDateTime updatedAfter, LocalDateTime updatedBefore,
                              Double userLatitude, Double userLongitude, Integer minDistance, Integer maxDistance,
                              Long hotelOwnerId, String name, HotelCategoryEnum category, String province, String city,
                              String district, String detailedAddress, Boolean parkingLot, Boolean wifi,
                              Boolean barbecue,
                              Boolean sauna, Boolean swimmingPool, Boolean restaurant, Boolean roofTop, Boolean fitness,
                              Boolean dryer, Boolean breakfast, Boolean smokingArea, Boolean allTimeDesk,
                              Boolean luggageStorage, Boolean snackBar, Boolean petFriendly, Integer people) {
        super(keyword, scoreAfter, idAfter, createdAfter, createdBefore, updatedAfter, updatedBefore);
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.hotelOwnerId = hotelOwnerId;
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
    }
}
