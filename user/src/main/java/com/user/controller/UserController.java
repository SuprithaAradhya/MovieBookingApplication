package com.user.controller;

import com.user.dto.UserDTO;
import com.user.entities.User;
import com.user.service.UserService;
import com.user.utils.POJOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "user_app/v1")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping(value="/users" , consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        User user = POJOConvertor.covertUserDTOToEntity(userDTO);

        User savedUser = userService.createUser(user);

        UserDTO savedUserDTO = POJOConvertor.covertUserEntityToDTO(user);

        return new ResponseEntity(savedUserDTO , HttpStatus.CREATED);
    }


    @GetMapping(value="/users" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity GetAllUsers(){
        List<User> userList = userService.getAllUsers();

        List<UserDTO>  userDTOList = new ArrayList<>();

        userList.forEach(u -> userDTOList.add(POJOConvertor.covertUserEntityToDTO(u)));

        return new ResponseEntity(userDTOList, HttpStatus.OK);

    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity getUser(@PathVariable(name = "id")  int id){
        User user = (userService.getUserBasedOnId(id));

        if(user!=null) {
            UserDTO userDTO = POJOConvertor.covertUserEntityToDTO(user);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        }else{
            return null;
        }
    }

    @PutMapping(value="/users" , consumes = MediaType.APPLICATION_JSON_VALUE ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser( @RequestBody UserDTO userDTO){

        User userToBeUpdated = POJOConvertor.covertUserDTOToEntity(userDTO);
        User savedUpdatedUser = userService.updateUser(userToBeUpdated);

        UserDTO user = POJOConvertor.covertUserEntityToDTO(savedUpdatedUser);

        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value="/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(name="id") int id){

        User user = userService.getUserBasedOnId(id);
        userService.deleteUser(user);

        return  new ResponseEntity(null, HttpStatus.OK);
    }

}
