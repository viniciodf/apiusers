package br.com.apiusers.ui.controllers;

import br.com.apiusers.shared.UserDto;
import br.com.apiusers.ui.controllers.ui.model.CreateUserRequestModel;
import br.com.apiusers.ui.controllers.ui.model.CreateUserResponseModel;
import br.com.apiusers.ui.controllers.ui.model.UserResponseModel;
import br.com.apiusers.ui.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "working on port " + env.getProperty("local.server.port") + ", with token = " +env.getProperty("token.secret") +env.getProperty("token.expiration_time");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetail) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetail, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }
}
