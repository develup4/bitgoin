package com.microservice.userservice.controller;

import com.microservice.userservice.dto.UserDto;
import com.microservice.userservice.service.UserService;
import com.microservice.userservice.vo.RequestUser;
import com.microservice.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String status() {
        return "fine " + env.getProperty("greeting.message");   // application.yml에 정의된 정보 얻어오기
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) { // RequestUser는 Vo이다. 관계형 데이터베이스의 레코드에 대응되는 ReadOnly 객체이다.
        // Model만으로 사용하지 않고 Dto나 Vo를 사용하는 이유는 여러가지있지만, 필요한 데이터가 정확히 매치되지도 않을뿐더러, Model이 달라지면 API도 함께 변하는 문제가 있을 수 있다.
        // 아무튼 Model, Dto, Vo간에는 비슷한 데이터를 가지고 있는데 타입은 달라서 단순 대입으로 데이터를 넣을 수 없고, Setter를 이용하기도 귀찮고, Setter를 만드는것도 바람직하지 않다.
        // 그래서 ModelMapper라는 녀석이 이러한 데이터 입력을 도와준다. ModelStruct라는 라이브러리도 많이 쓰이는거 같으니 따로 알아두자.
        // 사용법은 아래와 같다. 현재까지의 흐름은 JSON >> VO >> DTO이다.
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);

        // 레이어간 데이터를 전달할때 Dto를 사용한다.
        userService.createUser(userDto);

        // 보통 201 Created에는 생성한 데이터를 보내주는 것이 국룰이다. 이를 위해 Dto를 다시 Vo로 변환한다.
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
