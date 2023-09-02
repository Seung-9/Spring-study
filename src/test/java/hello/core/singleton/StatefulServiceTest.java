package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자 10000원을 주문함
        statefulService1.order("userA", 10000);

        // ThreadB: B사용자 20000원을 주문함
        statefulService2.order("userB", 20000);

        // ThreadA: A사용자가 주문 금액을 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);

        // 우리가 원하는 값은 10000원이지만 20000이 출력됨
        // 그 이유는 statefulService1이나 2 둘 다 같은 인스턴스를 사용하고 있음 == 비교를 하면 암.
    }


    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}