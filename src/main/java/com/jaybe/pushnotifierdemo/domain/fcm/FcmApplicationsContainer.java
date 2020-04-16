package com.jaybe.pushnotifierdemo.domain.fcm;

import com.jaybe.pushnotifierdemo.repositories.FirebaseConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class FcmApplicationsContainer {

    private List<FcmApplication> fcmApplications = Collections.synchronizedList(new ArrayList<>());
    private final FirebaseConfigRepository firebaseConfigRepository;

    public FcmApplicationsContainer(FirebaseConfigRepository firebaseConfigRepository) {
        this.firebaseConfigRepository = firebaseConfigRepository;
    }

    /**
     * Initialize all fcm applications stored in db
     * @param event contextRefreshed event called after context initialize
     */
    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        try {
            StreamSupport.stream(firebaseConfigRepository.findAll().spliterator(), false)
                    .forEach(fcmInitConfigEntity -> {
                        try {
                            fcmApplications.add(new FcmApplication(fcmInitConfigEntity));
                        } catch (Exception e) {
                            log.error(e.getLocalizedMessage());
                            // should publish event about broken config file
                        }
                    });
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            ((ConfigurableApplicationContext) event).close();
        }
    }

    public Optional<FcmApplication> getFcmAppByName(final String appName) {
        return fcmApplications.stream().filter(fcmApplication -> fcmApplication.getAppName().equalsIgnoreCase(appName))
                .findFirst();
    }
}
