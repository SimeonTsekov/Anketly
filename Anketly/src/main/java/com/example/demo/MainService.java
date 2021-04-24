package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MainService {
    List<ServiceType> allServiceTypes = null;
    List<Enrollment> enrollments = new ArrayList<>();
    List<Call> calls = new ArrayList<>();
    int nextCustomerId = 1;
    int seq = 0;

    public MainService() {
        try {
            loadServiceTypes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ServiceType> getAllServiceTypes() {
        return allServiceTypes;
    }

    public Enrollment enroll(UUID serviceId) {
        if (!allServiceTypes.stream().map(st -> st.getId()).anyMatch(id -> id.equals(serviceId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        long queueCount = enrollments.stream().filter(e -> e.getServiceId().equals(serviceId)).count();

        int customerId = nextCustomerId;
        nextCustomerId += 1;
        Enrollment enrollment = new Enrollment(serviceId, customerId, (int) (queueCount + 1));
        enrollments.add(enrollment);

        return enrollment;
    }

    public Call call(List<UUID> serviceIds, int deskNumber) {
        //Find the customer to call
        Optional<Integer> customer = enrollments.stream().filter(e -> serviceIds.contains(e.getServiceId())).map(e -> e.getCustomerId()).min(Integer::compareTo);
        if (!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No waiting customers.");
        }

        Enrollment enrollment = enrollments.stream().filter(e -> e.getCustomerId() == customer.get()).findFirst().get();
        Call call = new Call(enrollment.getServiceId(), customer.get(), deskNumber, seq++);
        enrollments.remove(enrollment);
        calls.add(call);

        return call;
    }

    public List<Info> info(int top) {
        return calls.stream().sorted(Comparator.comparingInt(Call::getSeq).reversed()).map(c -> new Info(c.getDeskNumber(), c.getCustomerId())).limit(top).collect(Collectors.toList());
    }

    private void loadServiceTypes() throws IOException {
        if (allServiceTypes == null) {
            try (InputStream resourceAsStream = MainService.class.getClassLoader().getResourceAsStream("services.json")) {
                ObjectMapper objectMapper = new ObjectMapper();
                allServiceTypes = Arrays.asList(objectMapper.readValue(resourceAsStream, ServiceType[].class));
                allServiceTypes.stream().forEach(st -> st.setId(UUID.randomUUID()));
            }
        }
    }
}
