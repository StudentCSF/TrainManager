package task8;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import task8.component.DumbComponent;
import task8.repository.DumbRepository;
import task8.service.AddStationToBranchService;
import task8.service.CrashOnStationService;
import task8.service.DumbService;
import task8.service.TrainArrivedAtStationService;

//@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = {DumbComponent.class, DumbService.class, DumbRepository.class})
public class Application {

    public static void initDB() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        AddStationToBranchService addStationToBranchService = context.getBean(AddStationToBranchService.class);
        CrashOnStationService crashOnStationService = context.getBean(CrashOnStationService.class);
        TrainArrivedAtStationService trainArrivedAtStationService = context.getBean(TrainArrivedAtStationService.class);

        initDB();
    }
}
