
import com.imos.common.utils.Scheduler;
import com.imos.common.utils.SchedulerTask;
import com.imos.common.utils.TimeUtils;
import java.util.Timer;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Pintu
 */
@Deprecated
public class SchedulerTest {

    @Test
    public void te() {
        SchedulerTask schedulerTask = new SchedulerTask((o) -> System.out.println(o), "Hello");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(schedulerTask,10000, new TimeUtils().getDelayed(new Scheduler.SchedulerBuilder().minute("1").build()));
//        timer.scheduleAtFixedRate(schedulerTask, 1000, 1000);
//        timer.scheduleAtFixedRate(schedulerTask, 10, new TimeUtils().getDelayed(new Scheduler.SchedulerBuilder().minute("1").build()));
    }
}
