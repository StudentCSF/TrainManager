package task8;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import task8.component.DumbComponent;
import task8.configuration.AppConfig;
import task8.exception.CrashNotFoundException;
import task8.exception.StationOnBranchNotFoundException;
import task8.exception.TrainOnBranchNotFoundException;
import task8.model.entity.*;
import task8.model.input.*;
import task8.repository.*;
import task8.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = {DumbComponent.class, DumbService.class, DumbRepository.class, AppConfig.class})
public class Application {

    private static final Random rnd = new Random();


    private static Runnable getRunnable(TrainOnBranchService trainOnBranchService,
                                        Integer trNum) {
        return () -> {
            while (true) {
                boolean cr = trainOnBranchService.canMoving(trNum);
                if (cr) {
                    try {
                        Thread.sleep(1000 * (rnd.nextInt(10) + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    trainOnBranchService.processRequest(
                            TrainArrivedAtStationRequest.builder()
                                    .dateTime(LocalDateTime.now())
                                    .trainNum(trNum)
                                    .build());
                }
            }
        };
    }

    private static Runnable getRunnable(CrashOnStationService crashOnStationService,
                                        CrashService crashService,
                                        StationService stationService) {

        return () -> {
            List<String> stations = stationService.getStationNames();
            List<String> crashes = crashService.getCrashNames();
            int i;
            while (true) {
                if ((i = rnd.nextInt(10 * stations.size())) < stations.size()) {
                    CrashOnStationRequest crashOnStationRequest = CrashOnStationRequest.builder()
                            .crashName(crashes.get(rnd.nextInt(crashes.size())))
                            .stationName(stations.get(i))
                            .dateTime(LocalDateTime.now())
                            .build();
                    UUID uid = crashOnStationService.processRequest(crashOnStationRequest);
                    try {
                        Thread.sleep(crashOnStationService.getCrashDifficulty(uid) * 100000);
                        crashOnStationService.repair(uid);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //this.crashOnStationRepository.delete(crashOnStationEntity.getUid());
                }
                try {
                    Thread.sleep(1000 * (rnd.nextInt(10) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static void initTrains(TrainService trainService, int n) {
        for (int i = 1; i <= n; i++) {
            trainService.addTrain(i);
        }
    }

    private static void initStations(StationService stationService) {
        stationService.addStation("Воронеж-1");
        stationService.addStation("Березовая роща");
        stationService.addStation("Отрожка");
        stationService.addStation("Боровская");
        stationService.addStation("Сомово");
        stationService.addStation("Дубовка");
        stationService.addStation("Шуберское");
        stationService.addStation("Синицыно");
        stationService.addStation("Тресвятская");
        stationService.addStation("Орлово");
        stationService.addStation("Углянец");
        stationService.addStation("Графская");
//        stationService.addStation("Бор");
//        stationService.addStation("Рамонь");
        stationService.addStation("Придача");
        stationService.addStation("Машмет");
        stationService.addStation("Масловка");
        stationService.addStation("Боево");
        stationService.addStation("Колодезная");
        stationService.addStation("Аношкино");
        stationService.addStation("Давыдовка");
        stationService.addStation("Бодеево");
        stationService.addStation("Блочный завод");
        stationService.addStation("Лиски");
        stationService.addStation("Плехановская");
        stationService.addStation("Воронеж-Курский");
        stationService.addStation("Подклетное");
        stationService.addStation("Семилуки");
        stationService.addStation("Латная");
        stationService.addStation("Ведуга");
        stationService.addStation("Кузиха");
        stationService.addStation("Курбатово");
        stationService.addStation("Избище");
        stationService.addStation("Нижнедевицк");
    }

    public static void initBranches(BranchService branchService) {
        branchService.addBranch("Воронеж-1 - Графская");
//        branchService.addBranch("Воронеж-1 - Рамонь");
        branchService.addBranch("Воронеж-1 - Лиски");
        branchService.addBranch("Воронеж-Курский - Нижнедевицк");
    }

    public static void initCrashes(CrashService crashService) {
        crashService.addCrash(CrashRequest.builder()
                .name("Какой-то кердык")
                .difficulty(1)
                .build());
        crashService.addCrash(CrashRequest.builder()
                .name("Полный кердык")
                .difficulty(2)
                .build());
        crashService.addCrash(CrashRequest.builder()
                .name("Полнейший кердык")
                .difficulty(3)
                .build());
        crashService.addCrash(CrashRequest.builder()
                .name("Апокалиптический кердык")
                .difficulty(4)
                .build());
    }

    public static void initStationsOnBranches(StationOnBranchService stationOnBranchService) {
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Воронеж-1")
                .position(0)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Воронеж-1")
                .position(0)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Березовая роща")
                .position(1)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Березовая роща")
                .position(1)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Отрожка")
                .position(2)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Отрожка")
                .position(2)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Боровская")
                .position(3)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Сомово")
                .position(4)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Дубовка")
                .position(5)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Шуберское")
                .position(6)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Синицыно")
                .position(7)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Тресвятская")
                .position(8)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Орлово")
                .position(9)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Углянец")
                .position(10)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Графская")
                .stationName("Графская")
                .position(11)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Придача")
                .position(3)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Машмет")
                .position(4)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Масловка")
                .position(5)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Боево")
                .position(6)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Колодезная")
                .position(7)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Аношкино")
                .position(8)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Давыдовка")
                .position(9)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Бодеево")
                .position(10)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Блочный завод")
                .position(11)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-1 - Лиски")
                .stationName("Лиски")
                .position(12)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Воронеж-Курский")
                .position(0)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Подклетное")
                .position(1)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Семилуки")
                .position(2)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Латная")
                .position(3)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Ведуга")
                .position(4)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Кузиха")
                .position(5)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Курбатово")
                .position(6)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Избище")
                .position(7)
                .build());
        stationOnBranchService.processRequest(AddStationToBranchRequest.builder()
                .branchName("Воронеж-Курский - Нижнедевицк")
                .stationName("Нижнедевицк")
                .position(8)
                .build());
    }

    public static void initTrainsOnBranch(TrainOnBranchService trainOnBranchService, TrainService trainService) {
        List<Integer> trainNums = trainService.getAllTrainNums();
        trainOnBranchService.insertTrainOnBranch(InsertTrainOnBranchRequest.builder()
                .trainNum(trainNums.get(0))
                .stationName("Воронеж-1")
                .branchName("Воронеж-1 - Графская")
                .build());
        trainOnBranchService.insertTrainOnBranch(InsertTrainOnBranchRequest.builder()
                .trainNum(trainNums.get(1))
                .stationName("Воронеж-1")
                .branchName("Воронеж-1 - Лиски")
                .build());
        trainOnBranchService.insertTrainOnBranch(InsertTrainOnBranchRequest.builder()
                .trainNum(trainNums.get(2))
                .stationName("Воронеж-Курский")
                .branchName("Воронеж-Курский - Нижнедевицк")
                .build());
    }

    public static void main(String[] args) {
        int n = 3;

        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        StationOnBranchService stationOnBranchService = context.getBean(StationOnBranchService.class);
        CrashOnStationService crashOnStationService = context.getBean(CrashOnStationService.class);
        TrainOnBranchService trainOnBranchService = context.getBean(TrainOnBranchService.class);
        TrainService trainService = context.getBean(TrainService.class);
        StationService stationService = context.getBean(StationService.class);
        BranchService branchService = context.getBean(BranchService.class);
        CrashService crashService = context.getBean(CrashService.class);

        initTrains(trainService, n);

        initStations(stationService);

        initBranches(branchService);

        initCrashes(crashService);

        initStationsOnBranches(stationOnBranchService);

        initTrainsOnBranch(trainOnBranchService, trainService);

        for (Integer num : trainService.getAllTrainNums()) new Thread(getRunnable(trainOnBranchService, num)).start();

        new Thread(getRunnable(crashOnStationService, crashService, stationService)).start();

    }
}
