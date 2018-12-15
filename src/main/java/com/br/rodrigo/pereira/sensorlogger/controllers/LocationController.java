package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.mappers.LocationMapper;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.LocationCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.LocationUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.services.LocationService;
import com.br.rodrigo.pereira.sensorlogger.services.ValidHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ValidHeaderService validHeaderService;

    @Autowired
    private LocationMapper locationMapper;

    @PostMapping(value = "/create")
    public ResponseEntity createLocation(@RequestHeader String token,
                                         @RequestBody LocationCreateRequest locationCreateRequest) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        locationService.createLocation(locationMapper.locationCreateRequestToLocationCreateData(locationCreateRequest), user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{locationName}")
    public ResponseEntity updateGeneralUser(@PathVariable String locationName, @RequestHeader String token,
                                            @RequestBody LocationUpdateRequest locationUpdateRequest) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        locationService.updateLocation(locationMapper.locationUpdateRequestToLocationUpdateData(locationUpdateRequest), user, locationName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete/{locationName}")
    public ResponseEntity deleteLocation(@PathVariable String locationName, @RequestHeader String token) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        locationService.deleteUser(locationName, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{locationName}")
    public ResponseEntity<Location> findSingleLocation(@RequestHeader String token, @PathVariable String locationName) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(locationService.getLocationByName(locationName, user));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Location>> findAllLocations(@RequestHeader String token) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(locationService.getLocationList(user));
    }

    @GetMapping(value = "/all/city/{name}")
    public ResponseEntity<List<Location>> findAllLocationsByCity(@RequestHeader String token, @PathVariable String name) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(locationService.getLocationListByType(user, "city", name));
    }

    @GetMapping(value = "/all/province/{name}")
    public ResponseEntity<List<Location>> findAllLocationsByProvince(@RequestHeader String token, @PathVariable String name) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(locationService.getLocationListByType(user, "province", name));
    }

    @GetMapping(value = "/all/country/{name}")
    public ResponseEntity<List<Location>> findAllLocationsByCountry(@RequestHeader String token, @PathVariable String name) {
        User user = validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(locationService.getLocationListByType(user, "country", name));
    }

}
