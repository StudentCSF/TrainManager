package task8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import task8.component.DumbComponent;
import task8.configuration.AppConfig;
import task8.exception.StationOnBranchNotFoundException;
import task8.exception.TrainOnBranchNotFoundException;
import task8.model.entity.*;
import task8.model.input.CrashOnStationRequest;
import task8.model.input.TrainArrivedAtStationRequest;
import task8.repository.*;
import task8.service.AddStationToBranchService;
import task8.service.CrashOnStationService;
import task8.service.DumbService;
import task8.service.TrainArrivedAtStationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = {DumbComponent.class, DumbService.class, DumbRepository.class, AppConfig.class})
public class Application {

    private static final Random rnd = new Random();


    private static Runnable getRunnable(TrainArrivedAtStationService trainArrivedAtStationService,
                                        TrainOnBranchRepository trainOnBranchRepository,
                                        StationOnBranchRepository stationOnBranchRepository,
                                        CrashOnStationRepository crashOnStationRepository,
                                        UUID trUid) {
        return () -> {
            while (true) {
                boolean cr = crashOnStationRepository.findByStationId(stationOnBranchRepository.findById(
                                trainOnBranchRepository.findByTrainId(trUid)
                                        .orElseThrow(TrainOnBranchNotFoundException::new).getStOnBrUid())
                        .orElseThrow(StationOnBranchNotFoundException::new).getStUid()).isPresent();
                if (!cr) {
                    try {
                        Thread.sleep(1000 * (rnd.nextInt(10) + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    trainArrivedAtStationService.processRequest(
                            TrainArrivedAtStationRequest.builder()
                                    .dateTime(LocalDateTime.now())
                                    .trainUid(trUid)
                                    .build());
                }
            }
        };
    }

    private static Runnable getRunnable(CrashOnStationService crashOnStationService,
                                        CrashRepository crashRepository,
                                        StationRepository stationRepository) {

        return () -> {
            List<StationEntity> stations = stationRepository.findAll();
            List<CrashEntity> crashes = crashRepository.findAll();
            int i;
            while (true) {
                if ((i = rnd.nextInt(10 * stations.size())) < stations.size()) {
                    CrashOnStationRequest crashOnStationRequest = CrashOnStationRequest.builder()
                            .crashName(crashes.get(rnd.nextInt(crashes.size())).getName())
                            .stationName(stations.get(i).getName())
                            .dateTime(LocalDateTime.now())
                            .build();
                    crashOnStationService.processRequest(crashOnStationRequest);
                }
                try {
                    Thread.sleep(1000 * (rnd.nextInt(10) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        AddStationToBranchService addStationToBranchService = context.getBean(AddStationToBranchService.class);
        CrashOnStationService crashOnStationService = context.getBean(CrashOnStationService.class);
        TrainArrivedAtStationService trainArrivedAtStationService = context.getBean(TrainArrivedAtStationService.class);

        BranchRepository branchRepository = context.getBean(BranchRepository.class);
        CrashOnStationRepository crashOnStationRepository = context.getBean(CrashOnStationRepository.class);
        CrashRepository crashRepository = context.getBean(CrashRepository.class);
        StationOnBranchRepository stationOnBranchRepository = context.getBean(StationOnBranchRepository.class);
        TrainOnBranchRepository trainOnBranchRepository = context.getBean(TrainOnBranchRepository.class);
        TrainRepository trainRepository = context.getBean(TrainRepository.class);
        StationRepository stationRepository = context.getBean(StationRepository.class);

        UUID voronezh1Uid = UUID.randomUUID();
        stationRepository.insert(StationEntity.builder()
                .uid(voronezh1Uid)
                .name("Воронеж 1")
                .build());
        UUID berUid = UUID.randomUUID();
        stationRepository.insert(StationEntity.builder()
                .uid(berUid)
                .name("Березовая роща")
                .build());
        UUID otrUid = UUID.randomUUID();
        stationRepository.insert(StationEntity.builder()
                .uid(otrUid)
                .name("Отрожка")
                .build());
        UUID mashUid = UUID.randomUUID();
        stationRepository.insert(StationEntity.builder()
                .uid(mashUid)
                .name("Машмет")
                .build());
        UUID borovUid = UUID.randomUUID();
        stationRepository.insert(StationEntity.builder()
                .uid(borovUid)
                .name("Боровская")
                .build());

        UUID t1Uid = UUID.randomUUID();
        trainRepository.insert(TrainEntity.builder()
                .uid(t1Uid)
                .number(1)
                .build());
        UUID t2Uid = UUID.randomUUID();
        trainRepository.insert(TrainEntity.builder()
                .uid(t2Uid)
                .number(2)
                .build());

        UUID cr1Uid = UUID.randomUUID();
        crashRepository.insert(CrashEntity.builder()
                .uid(cr1Uid)
                .name("Какой-то кердык")
                .difficulty(1)
                .build());
        UUID cr2Uid = UUID.randomUUID();
        crashRepository.insert(CrashEntity.builder()
                .uid(cr2Uid)
                .name("Полный кердык")
                .difficulty(2)
                .build());

        UUID brGrUid = UUID.randomUUID();
        branchRepository.insert(BranchEntity.builder()
                .name("Воронеж 1 - Графская")
                .uid(brGrUid)
                .build());
        UUID brLUid = UUID.randomUUID();
        branchRepository.insert(BranchEntity.builder()
                .name("Воронеж 1 - Лиски")
                .uid(brLUid)
                .build());

        UUID vOnGrUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(vOnGrUid)
                .stUid(voronezh1Uid)
                .position(0)
                .brUid(brGrUid)
                .build());
        UUID vOnLUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(vOnLUid)
                .stUid(voronezh1Uid)
                .position(0)
                .brUid(brLUid)
                .build());
        UUID berOnGrUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(berOnGrUid)
                .stUid(berUid)
                .position(1)
                .brUid(brGrUid)
                .build());
        UUID berOnLUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(berOnLUid)
                .stUid(berUid)
                .position(1)
                .brUid(brLUid)
                .build());
        UUID otrOnGrUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(otrOnGrUid)
                .stUid(otrUid)
                .position(2)
                .brUid(brGrUid)
                .build());
        UUID otrOnLUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(otrOnLUid)
                .stUid(otrUid)
                .position(2)
                .brUid(brLUid)
                .build());
        UUID mashOnLUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(mashOnLUid)
                .stUid(mashUid)
                .position(3)
                .brUid(brLUid)
                .build());
        UUID borovOnGrUid = UUID.randomUUID();
        stationOnBranchRepository.insert(StationOnBranchEntity.builder()
                .uid(borovOnGrUid)
                .stUid(borovUid)
                .position(3)
                .brUid(brGrUid)
                .build());

        trainOnBranchRepository.insert(TrainOnBranchEntity.builder()
                .trUid(t1Uid)
                .stOnBrUid(vOnGrUid)
                .forward(true)
                .datetime(LocalDateTime.now())
                .build());
        trainOnBranchRepository.insert(TrainOnBranchEntity.builder()
                .trUid(t2Uid)
                .stOnBrUid(vOnLUid)
                .forward(true)
                .datetime(LocalDateTime.now())
                .build());

        Thread thread1 = new Thread(
                getRunnable(
                        trainArrivedAtStationService,
                        trainOnBranchRepository,
                        stationOnBranchRepository,
                        crashOnStationRepository,
                        t1Uid)
        );
        Thread thread2 = new Thread(
                getRunnable(
                        trainArrivedAtStationService,
                        trainOnBranchRepository,
                        stationOnBranchRepository,
                        crashOnStationRepository,
                        t2Uid)
        );
//        Thread thread2 = new Thread(getRunnable(trainArrivedAtStationService, trainOnBranchRepository, stationOnBranchRepository, crashOnStationRepository, t2Uid));
//        Thread thread2 = new Thread(getRunnable(trainArrivedAtStationService, t2Uid));
        Thread thread3 = new Thread(getRunnable(crashOnStationService, crashRepository, stationRepository));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
