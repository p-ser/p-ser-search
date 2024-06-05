package com.pser.search.dao;

import co.elastic.clients.elasticsearch._types.DistanceUnit;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.pser.search.Util;
import com.pser.search.dao.SearchDao.SearchQueryArgument;
import com.pser.search.domain.Auction;
import com.pser.search.domain.AuctionStatusEnum;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.AuctionSearchRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class AuctionDaoImpl implements AuctionDaoCustom {
    private final SearchDao searchDao;

    @Override
    public SearchSlice<Auction> search(AuctionSearchRequest request, Pageable pageable) {
        Builder boolQueryBuilder = QueryBuilders.bool();
        setKeywordQuery(boolQueryBuilder, request);
        setAuctionFilterQuery(boolQueryBuilder, request);
        setReservationFilterQuery(boolQueryBuilder, request);
        setOwnerFilterQuery(boolQueryBuilder, request);
        setHotelFilterQuery(boolQueryBuilder, request);
        setFacilityFilterQuery(boolQueryBuilder, request);

        SearchQueryArgument<Auction> searchQueryArgument = SearchQueryArgument.<Auction>builder()
                .mappingClass(Auction.class)
                .pageable(pageable)
                .boolQueryBuilder(boolQueryBuilder)
                .scoreAfter(request.getScoreAfter())
                .idAfter(request.getIdAfter())
                .build();
        return searchDao.search(searchQueryArgument);
    }

    private void setAuctionFilterQuery(Builder builder, AuctionSearchRequest request) {
        if (request.getPrice() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(request.getPrice())
                                    .field("price")
                    )
            );
        }
        if (request.getPriceGte() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(JsonData.of(request.getPriceGte()))
                                    .field("price")
                    )
            );
        }
        if (request.getPriceLte() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(JsonData.of(request.getPriceLte()))
                                    .field("price")
                    )
            );
        }

        if (request.getDepositPrice() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(request.getDepositPrice())
                                    .field("depositPrice")
                    )
            );
        }
        if (request.getDepositPriceGte() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(JsonData.of(request.getDepositPriceGte()))
                                    .field("depositPrice")
                    )
            );
        }
        if (request.getDepositPriceLte() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(JsonData.of(request.getDepositPriceLte()))
                                    .field("depositPrice")
                    )
            );
        }

        if (request.getIsOngoing() != null) {
            builder.filter(
                    f -> f.term(
                            r -> r.value(AuctionStatusEnum.CREATED.getValue())
                                    .field("status")
                    )
            );
            builder.filter(
                    f -> f.range(
                            r -> r.gt(Util.toJsonData(LocalDateTime.now()))
                                    .field("endAt")
                    )
            );
        }
    }

    private void setReservationFilterQuery(Builder outerBuilder, AuctionSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "reservation";

        if (request.getStartAt() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(Util.toFormattedString(request.getStartAtAfter()))
                                    .field("%s.%s".formatted(path, "startAt"))
                    )
            );
        }
        if (request.getStartAtAfter() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(Util.toJsonData(request.getStartAtAfter()))
                                    .field("%s.%s".formatted(path, "startAt"))
                    )
            );
        }
        if (request.getStartAtBefore() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(Util.toJsonData(request.getStartAtBefore()))
                                    .field("%s.%s".formatted(path, "startAt"))
                    )
            );
        }

        if (request.getEndAt() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(Util.toFormattedString(request.getEndAt()))
                                    .field("%s.%s".formatted(path, "endAt"))
                    )
            );
        }
        if (request.getEndAtAfter() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(Util.toJsonData(request.getEndAtAfter()))
                                    .field("%s.%s".formatted(path, "endAt"))
                    )
            );
        }
        if (request.getEndAtBefore() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(Util.toJsonData(request.getEndAtBefore()))
                                    .field("%s.%s".formatted(path, "endAt"))
                    )
            );
        }

        outerBuilder.should(
                m -> m.nested(
                        n -> n
                                .path(path)
                                .query(q -> q.bool(builder.build()))
                )
        );
    }

    private void setKeywordQuery(Builder outerBuilder, AuctionSearchRequest request) {
        Builder hotelQueryBuilder = QueryBuilders.bool();
        Builder roomQueryBuilder = QueryBuilders.bool();
        String hotelPath = "reservation.hotel";
        String roomPath = "reservation.room";

        hotelQueryBuilder.must(
                m -> m.multiMatch(
                        mm -> mm.fields("%s.%s^3".formatted(hotelPath, "name"),
                                        "%s.%s".formatted(hotelPath, "description"),
                                        "%s.%s".formatted(hotelPath, "notice"))
                                .query(request.getKeyword())
                )
        );

        roomQueryBuilder.must(
                m -> m.multiMatch(
                        mm -> mm.fields("%s.%s^3".formatted(roomPath, "name"),
                                        "%s.%s".formatted(roomPath, "description"),
                                        "%s.%s".formatted(roomPath, "precaution"))
                                .query(request.getKeyword())
                )
        );

        BoolQuery hotelQuery = hotelQueryBuilder.build();
        if (hotelQuery.must().isEmpty() && hotelQuery.should().isEmpty()) {
            return;
        }
        outerBuilder.should(
                s -> s.nested(
                        n -> n
                                .path(hotelPath)
                                .query(q -> q.bool(hotelQuery))
                )
        );

        BoolQuery roomQuery = roomQueryBuilder.build();
        if (roomQuery.must().isEmpty() && roomQuery.should().isEmpty()) {
            return;
        }
        outerBuilder.should(
                m -> m.nested(
                        n -> n
                                .path(roomPath)
                                .query(q -> q.bool(roomQuery))
                )
        );
    }

    private void setOwnerFilterQuery(Builder outerBuilder, AuctionSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "reservation";

        if (request.getUserId() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("%s.%s".formatted(path, "userId"))
                            .query(request.getUserId())
            ));
        }

        outerBuilder.filter(
                m -> m.nested(
                        n -> n
                                .path(path)
                                .query(q -> q.bool(builder.build()))
                )
        );
    }

    private void setHotelDistanceQuery(Builder builder, AuctionSearchRequest request) {
        if (request.getUserLatitude() == null || request.getUserLongitude() == null) {
            return;
        }

        Integer maxDistance = Optional.ofNullable(request.getMaxDistance()).orElse(5);
        Integer minDistance = request.getMinDistance();

        builder.must(buildDistanceQuery(request, maxDistance));
        if (minDistance != null) {
            builder.mustNot(buildDistanceQuery(request, request.getMinDistance()));
        }
    }

    private void setHotelFilterQuery(Builder outerBuilder, AuctionSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "reservation.hotel";

        setHotelDistanceQuery(builder, request);
        if (request.getName() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "name")
                            .query(request.getName())
            ));
        }
        if (request.getCategory() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "category")
                            .query(request.getCategory().getValue())
            ));
        }
        if (request.getProvince() != null) {
            builder.filter(f -> f.term(
                    m -> m
                            .field(path + "." + "province")
                            .value(request.getProvince())
            ));
        }
        if (request.getCity() != null) {
            builder.filter(f -> f.term(
                    m -> m
                            .field(path + "." + "city")
                            .value(request.getCity())
            ));
        }
        if (request.getDistrict() != null) {
            builder.filter(f -> f.term(
                    m -> m
                            .field(path + "." + "district")
                            .value(request.getDistrict())
            ));
        }
        if (request.getDetailedAddress() != null) {
            builder.filter(f -> f.term(
                    m -> m
                            .field(path + "." + "detailedAddress")
                            .value(request.getDetailedAddress())
            ));
        }

        BoolQuery query = builder.build();
        if (query.must().isEmpty()) {
            return;
        }
        outerBuilder.filter(
                m -> m.nested(
                        n -> n
                                .path(path)
                                .query(q -> q.bool(query))
                )
        );
    }

    private void setFacilityFilterQuery(Builder outerBuilder, AuctionSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "reservation.hotel.facility";

        if (request.getParkingLot() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "parkingLot")
                            .query(request.getParkingLot())
            ));
        }
        if (request.getWifi() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "wifi")
                            .query(request.getWifi())
            ));
        }
        if (request.getBarbecue() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "barbecue")
                            .query(request.getBarbecue())
            ));
        }
        if (request.getSauna() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "sauna")
                            .query(request.getSauna())
            ));
        }
        if (request.getSwimmingPool() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "swimmingPool")
                            .query(request.getSwimmingPool())
            ));
        }
        if (request.getRestaurant() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "restaurant")
                            .query(request.getRestaurant())
            ));
        }
        if (request.getRoofTop() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "roofTop")
                            .query(request.getRoofTop())
            ));
        }
        if (request.getFitness() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "fitness")
                            .query(request.getFitness())
            ));
        }
        if (request.getDryer() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "dryer")
                            .query(request.getDryer())
            ));
        }
        if (request.getBreakfast() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "breakfast")
                            .query(request.getBreakfast())
            ));
        }
        if (request.getSmokingArea() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "smokingArea")
                            .query(request.getSmokingArea())
            ));
        }
        if (request.getAllTimeDesk() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "allTimeDesk")
                            .query(request.getAllTimeDesk())
            ));
        }
        if (request.getLuggageStorage() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "luggageStorage")
                            .query(request.getLuggageStorage())
            ));
        }
        if (request.getSnackBar() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "snackBar")
                            .query(request.getSnackBar())
            ));
        }
        if (request.getPetFriendly() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field(path + "." + "petFriendly")
                            .query(request.getPetFriendly())
            ));
        }

        BoolQuery query = builder.build();
        if (query.must().isEmpty()) {
            return;
        }
        outerBuilder.filter(
                m -> m.nested(
                        n -> n
                                .path(path)
                                .query(q -> q.bool(query))
                )
        );
    }

    private Query buildDistanceQuery(AuctionSearchRequest request, int distance) {
        LatLonGeoLocation location = LatLonGeoLocation.of(
                ll -> ll.lat(request.getUserLatitude()).lon(request.getUserLongitude())
        );
        String formattedDistance = "%d%s".formatted(distance, DistanceUnit.Kilometers.jsonValue());
        return QueryBuilders.geoDistance(geo ->
                geo
                        .field("reservation.hotel.location")
                        .distance(formattedDistance)
                        .distanceType(GeoDistanceType.Arc)
                        .location(loc -> loc.latlon(location))
        );
    }
}
