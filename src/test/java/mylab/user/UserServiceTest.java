package mylab.user;

// JUnit 5 테스트 관련 라이브러리
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Spring 의존성 주입 및 테스트 관련 라이브러리
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Assertion(검증) 메서드를 사용하기 위한 static import
import static org.junit.jupiter.api.Assertions.*;

// JUnit 5와 Spring Test를 연결한다.
@ExtendWith(SpringExtension.class)

// Spring Container를 생성할 때 사용할 XML 설정 파일을 지정한다.
// classpath:는 클래스패스에서 해당 파일을 찾으라는 의미이다.
@ContextConfiguration(locations = "classpath:mylab-user-di.xml")

public class UserServiceTest {

    // Spring Container에 등록된 UserService Bean을 자동 주입받는다.
    // 직접 new UserService()를 하지 않아도 된다.
    @Autowired
    private UserService userService;


    // 1. DI(Dependency Injection)가 정상적으로 이루어졌는지 검증
    @Test
    public void testDependencyInjection() {

        // UserService 객체가 정상적으로 주입되었는지 확인
        // null이 아니면 테스트 성공
        assertNotNull(userService);

        // UserService 내부에 UserRepository 객체가
        // 정상적으로 주입되었는지 확인
        assertNotNull(userService.getUserRepository());

        // @Value("MySQL")을 통해 dbType에
        // MySQL이라는 문자열이 정상적으로 주입되었는지 확인
        // assertEquals(기대값, 실제값)
        assertEquals("MySQL",
                userService.getUserRepository().getDbType());

        // UserService 내부에 SecurityService 객체가
        // 정상적으로 주입되었는지 확인
        assertNotNull(userService.getSecurityService());
    }


    // 2. 사용자 등록 성공 테스트
    @Test
    public void testRegisterUserSuccess() {

        // 사용자 ID, 이름, 비밀번호를 전달한다.
        // 비밀번호가 존재하므로 인증에 성공해야 한다.
        // 인증 성공 후 UserRepository에서 사용자 정보를 저장한다.
        // registerUser()의 반환값이 true인지 검증한다.
        assertTrue(userService.registerUser(
                "user01", "Kim", "1234"));
    }


    // 3. 사용자 등록 실패 테스트
    @Test
    public void testRegisterUserFailure() {

        // 비밀번호에 null을 전달하여 인증 실패 상황을 만든다.
        // 인증에 실패하면 사용자 정보는 저장되지 않는다.
        // registerUser()의 반환값이 false인지 검증한다.
        assertFalse(userService.registerUser(
                "user02", "Lee", null));
    }
}