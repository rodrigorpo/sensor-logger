package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.LocationCreateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.LocationUpdateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.LocationRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LogRepository logRepository;

    public void createLocation(LocationCreateData locationCreateData, User user) {

        Location locationExists = locationRepository.findByName(locationCreateData.getName());
        if (locationExists != null) {
            BusinessException businessException = new BusinessException("Localidade já está em uso!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.INSERT, user, null, locationExists, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }

        Location newLocation = locationRepository.save(new Location(
                locationCreateData.getName(),
                locationCreateData.getCity(),
                locationCreateData.getProvince(),
                locationCreateData.getCountry()));
        logRepository.save(new Log(OperationType.INSERT, user, null, newLocation, new HttpDocument(HttpStatus.OK)));
    }

    public void updateLocation(LocationUpdateData locationUpdateData, User user, String locationName) {
        Location locationExists = verifyLocation(locationName, user, OperationType.UPDATE);
        Location newLocationExists = locationRepository.findByName(locationUpdateData.getNewName());
        if (newLocationExists != null) {
            BusinessException businessException = new BusinessException("Localidade já está em uso!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.UPDATE, user, null, newLocationExists, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        Location newLocation = updateLocationGeneric(locationExists, locationUpdateData);
        logRepository.save(new Log(OperationType.UPDATE, user, null, newLocation, new HttpDocument(HttpStatus.OK)));
    }

    public void deleteUser(String locationName, User user) {
        Location locationToDelete = verifyLocation(locationName, user, OperationType.DELETE);
        if (!user.equals(logRepository.findByLocation(locationToDelete).getUser()) && user.getPrivileges().equals(Privileges.NORMAL)) {
            BusinessException businessException = new BusinessException("Não foi você que inseriu essa localização!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.DELETE, user, null, locationToDelete, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        locationRepository.delete(locationToDelete);
        logRepository.save(new Log(OperationType.DELETE, user, null, locationToDelete, new HttpDocument(HttpStatus.OK)));
    }

    public Location getLocationByName(String locationName, User user) {
        Location location = verifyLocation(locationName, user, OperationType.SELECT);
        logRepository.save(new Log(OperationType.SELECT, user, null, location, new HttpDocument(HttpStatus.OK)));
        return location;
    }

    public List<Location> getLocationList(User user) {
        logRepository.save(new Log(OperationType.SELECT, user, null, null, new HttpDocument(HttpStatus.OK)));
        return locationRepository.findAll();
    }

    public List<Location> getLocationListByType(User user, String type, String name) {
        List<Location> locations = new ArrayList<>();
        switch (type) {
            case "city":
                locations = locationRepository.findByCity(name);
                break;
            case "province":
                locations = locationRepository.findByProvince(name);
                break;
            case "country":
                locations = locationRepository.findByCountry(name);
                break;
        }
        logRepository.save(new Log(OperationType.SELECT, user, null, null, new HttpDocument(HttpStatus.OK)));
        return locations;
    }

    private Location verifyLocation(String locationName, User user, OperationType operationType) {
        Location locationExists = locationRepository.findByName(locationName);
        if (locationExists == null) {
            BusinessException businessException = new BusinessException("Localidade não encontrada", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(operationType, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        return locationExists;
    }

    private Location updateLocationGeneric(Location location, LocationUpdateData locationUpdateData) {
        location.setName(locationUpdateData.getNewName());
        location.setCity(locationUpdateData.getCity());
        location.setProvince(locationUpdateData.getProvince());
        location.setCountry(locationUpdateData.getCountry());
        return locationRepository.save(location);
    }
}
