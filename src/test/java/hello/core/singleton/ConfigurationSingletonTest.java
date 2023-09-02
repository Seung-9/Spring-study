package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        // 3개의 출력 모두 같은 인스턴스가 조회됨.
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // class hello.core.AppConfig 라고 출력되어야 하지만 다른 식으로 출력이 됨.
        // CGLIB 라이브러리를 사용하여 AppConfig 클래스를 상속받은 다른 클래스를 만들고, 그 클래스를 스프링 빈으로 등록하기 때문임.
        // 만들어진 새로운 클래스 로직은 이미 컨테이너에 존재하면 컨테이너에 있는 것을 반환하고, 그렇지 않으면 스프링 컨테이너에 등록하고 반환하는 로직
        // 그러므로 AppConfig에서 call AppConfig.memberRepository 이 한 번만 실행되는 것.
        // 만약 AppConfig 에 @Configuration 사용하지 않으면 CGLIB 라이브러리를 사용하지 않음 => 싱글톤이 꺠짐
        System.out.println("bean = " + bean.getClass());
    }
}
