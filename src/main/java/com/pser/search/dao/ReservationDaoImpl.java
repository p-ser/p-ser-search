package com.pser.search.dao;

import co.elastic.clients.elasticsearch._types.DistanceUnit;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.pser.search.Util;
import com.pser.search.dao.SearchDao.SearchQueryArgument;
import com.pser.search.domain.Reservation;
import com.pser.search.dto.SearchSlice;
import com.pser.search.dto.request.ReservationSearchRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ReservationDaoImpl implements ReservationDaoCustom {
    private final SearchDao searchDao;

    @Override
    public SearchSlice<Reservation> search(ReservationSearchRequest request, Pageable pageable) {
        Builder boolQueryBuilder = QueryBuilders.bool();
        setKeywordQuery(boolQueryBuilder, request);
        setOwnerFilterQuery(boolQueryBuilder, request);
        setReservationQuery(boolQueryBuilder, request);
        setHotelFilterQuery(boolQueryBuilder, request);
        setFacilityFilterQuery(boolQueryBuilder, request);

        SearchQueryArgument<Reservation> searchQueryArgument = SearchQueryArgument.<Reservation>builder()
                .mappingClass(Reservation.class)
                .pageable(pageable)
                .boolQueryBuilder(boolQueryBuilder)
                .scoreAfter(request.getScoreAfter())
                .idAfter(request.getIdAfter())
                .build();
        return searchDao.search(searchQueryArgument);
    }

    private void setKeywordQuery(Builder outerBuilder, ReservationSearchRequest request) {
        if (request.getKeyword() == null) {
            return;
        }

        Builder hotelQueryBuilder = QueryBuilders.bool();
        Builder roomQueryBuilder = QueryBuilders.bool();
        String hotelPath = "hotel";
        String roomPath = "room";

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

    private void setReservationQuery(Builder builder, ReservationSearchRequest request) {
        if (request.getStartAt() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(Util.toFormattedString(request.getStartAtAfter()))
                                    .field("startAt")
                    )
            );
        }
        if (request.getStartAtAfter() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(Util.toJsonData(request.getStartAtAfter()))
                                    .field("startAt")
                    )
            );
        }
        if (request.getStartAtBefore() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(Util.toJsonData(request.getStartAtBefore()))
                                    .field("startAt")
                    )
            );
        }

        if (request.getEndAt() != null) {
            builder.filter(
                    f -> f.match(
                            r -> r.query(Util.toFormattedString(request.getEndAt()))
                                    .field("endAt")
                    )
            );
        }
        if (request.getEndAtAfter() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.gte(Util.toJsonData(request.getEndAtAfter()))
                                    .field("endAt")
                    )
            );
        }
        if (request.getEndAtBefore() != null) {
            builder.filter(
                    f -> f.range(
                            r -> r.lte(Util.toJsonData(request.getEndAtBefore()))
                                    .field("endAt")
                    )
            );
        }
    }

    private void setOwnerFilterQuery(Builder builder, ReservationSearchRequest request) {
        if (request.getUserId() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("userId")
                            .query(request.getUserId())
            ));
        }
    }

    private void setHotelDistanceQuery(Builder builder, ReservationSearchRequest request) {
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

    private void setHotelFilterQuery(Builder outerBuilder, ReservationSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "hotel";

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

    private void setFacilityFilterQuery(Builder outerBuilder, ReservationSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        String path = "hotel.facility";

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

    private Query buildDistanceQuery(ReservationSearchRequest request, int distance) {
        LatLonGeoLocation location = LatLonGeoLocation.of(
                ll -> ll.lat(request.getUserLatitude()).lon(request.getUserLongitude())
        );
        String formattedDistance = "%d%s".formatted(distance, DistanceUnit.Kilometers.jsonValue());
        return QueryBuilders.geoDistance(geo ->
                geo
                        .field("hotel.location")
                        .distance(formattedDistance)
                        .distanceType(GeoDistanceType.Arc)
                        .location(loc -> loc.latlon(location))
        );
    }
}
