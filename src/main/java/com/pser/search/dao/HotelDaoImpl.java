package com.pser.search.dao;

import co.elastic.clients.elasticsearch._types.DistanceUnit;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.pser.search.domain.Hotel;
import com.pser.search.dto.request.HotelSearchRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;

@RequiredArgsConstructor
public class HotelDaoImpl implements HotelDaoCustom {
    private final ElasticsearchOperations operations;

    @Override
    public Slice<Hotel> search(HotelSearchRequest request, Pageable pageable) {
        List<Object> searchAfter = List.of(request.getScoreAfter(), request.getIdAfter());

        Builder boolQueryBuilder = QueryBuilders.bool();
        setOwnerFilterQuery(boolQueryBuilder, request);
        setDistanceQuery(boolQueryBuilder, request);
        setHotelFilterQuery(boolQueryBuilder, request);
        setFacilityFilterQuery(boolQueryBuilder, request);

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .withSearchAfter(searchAfter)
                .withMaxResults(pageable.getPageSize() + 1)
                .withSort(s ->
                        s.field(f -> f
                                .field("_score")
                                .order(SortOrder.Desc)
                        )
                )
                .withSort(s ->
                        s.field(f -> f
                                .field("id")
                                .order(SortOrder.Asc)
                        )
                )
                .build();
        List<Hotel> result = operations.search(query, Hotel.class)
                .map(SearchHit::getContent)
                .stream()
                .collect(Collectors.toList());

        boolean hasNext = result.size() > pageable.getPageSize();
        if (hasNext) {
            result.remove(result.size() - 1);
        }
        return new SliceImpl<>(result, pageable, hasNext);
    }

    private void setOwnerFilterQuery(Builder builder, HotelSearchRequest request) {
        if (request.getUserId() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("userId")
                            .query(request.getUserId())
            ));
        }
    }

    private void setDistanceQuery(Builder builder, HotelSearchRequest request) {
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

    private void setHotelFilterQuery(Builder builder, HotelSearchRequest request) {
        if (request.getName() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("name")
                            .query(request.getName())
            ));
        }
        if (request.getCategory() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("category")
                            .query(request.getCategory().getValue())
            ));
        }
        if (request.getProvince() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("province.keyword")
                            .query(request.getProvince())
            ));
        }
        if (request.getCity() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("city.keyword")
                            .query(request.getCity())
            ));
        }
        if (request.getDistrict() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("district.keyword")
                            .query(request.getDistrict())
            ));
        }
        if (request.getDetailedAddress() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("detailedAddress.keyword")
                            .query(request.getDetailedAddress())
            ));
        }
    }

    private void setFacilityFilterQuery(Builder outerBuilder, HotelSearchRequest request) {
        Builder builder = QueryBuilders.bool();
        if (request.getParkingLot() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.parkingLot")
                            .query(request.getParkingLot())
            ));
        }
        if (request.getWifi() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.wifi")
                            .query(request.getWifi())
            ));
        }
        if (request.getBarbecue() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.barbecue")
                            .query(request.getBarbecue())
            ));
        }
        if (request.getSauna() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.sauna")
                            .query(request.getSauna())
            ));
        }
        if (request.getSwimmingPool() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.swimmingPool")
                            .query(request.getSwimmingPool())
            ));
        }
        if (request.getRestaurant() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.restaurant")
                            .query(request.getRestaurant())
            ));
        }
        if (request.getRoofTop() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.roofTop")
                            .query(request.getRoofTop())
            ));
        }
        if (request.getFitness() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.fitness")
                            .query(request.getFitness())
            ));
        }
        if (request.getDryer() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.dryer")
                            .query(request.getDryer())
            ));
        }
        if (request.getBreakfast() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.breakfast")
                            .query(request.getBreakfast())
            ));
        }
        if (request.getSmokingArea() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.smokingArea")
                            .query(request.getSmokingArea())
            ));
        }
        if (request.getAllTimeDesk() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.allTimeDesk")
                            .query(request.getAllTimeDesk())
            ));
        }
        if (request.getLuggageStorage() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.luggageStorage")
                            .query(request.getLuggageStorage())
            ));
        }
        if (request.getSnackBar() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.snackBar")
                            .query(request.getSnackBar())
            ));
        }
        if (request.getPetFriendly() != null) {
            builder.filter(f -> f.match(
                    m -> m
                            .field("facility.petFriendly")
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
                                .path("facility")
                                .query(q -> q.bool(query))
                )
        );
    }

    private Query buildDistanceQuery(HotelSearchRequest request, int distance) {
        LatLonGeoLocation location = LatLonGeoLocation.of(
                ll -> ll.lat(request.getUserLatitude()).lon(request.getUserLongitude())
        );
        String formattedDistance = "%d%s".formatted(distance, DistanceUnit.Kilometers.jsonValue());
        return QueryBuilders.geoDistance(geo ->
                geo
                        .field("location")
                        .distance(formattedDistance)
                        .distanceType(GeoDistanceType.Arc)
                        .location(loc -> loc.latlon(location))
        );
    }
}
