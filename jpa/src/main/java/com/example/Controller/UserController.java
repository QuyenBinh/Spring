// package com.example.Controller;

// import java.net.http.HttpResponse;
// import java.util.List;
// import java.util.Optional;

// import javax.annotation.PostConstruct;
// import javax.websocket.server.PathParam;

// import com.example.Rebository.UserRebository;
// import com.example.UserSevice.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.PutMapping;

// import com.example.Entity.Users;
// import com.example.Exception.ExceptionValid;

// @RestController
// @RequestMapping("/user")
// public class UserController {
//     @Autowired
//     private UserService userservice;
//     @Autowired
//     private UserRebository userRebository;
//     @GetMapping("/list")
//     public ResponseEntity<?> ListUser()    {
//         List<Users> users =   userservice.listUsers();
//         return ResponseEntity.ok(users);
//     }
//     @PostMapping(value="/add")
//     public ResponseEntity<?> addUser(@RequestBody Users entity) {
//         //TODO: process POST request
//         Users user = userservice.addUser(entity);
//         return ResponseEntity.ok(user);
//     }
//     @GetMapping("/user/{id}")   
//     public ResponseEntity<?> getUser(@PathVariable("id") long id) {
//         Optional<Users> user = userservice.getUser(id);
//         if(user.isPresent())    {
//             return new ResponseEntity<>(user.get(),HttpStatus.OK);
//         }
//         else{
//             throw new ExceptionValid("Khong ton tai user");
//         }
//     }
//     @PutMapping("/edit/{id}")
//     public ResponseEntity<?> editUser(@RequestBody Users entity,@PathVariable("id") long id)  {
//         Optional<Users> data = userservice.getUser(id);
//         if(data.isPresent())    {
//             Users user = data.get();
//             user.setUsername(entity.getUsername());
//             user.setEmail(entity.getEmail());
//             user.setPassword(entity.getPassword());
//             return new ResponseEntity<>(userRebository.save(user), HttpStatus.OK);
//         }
//         else{
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         }
       
//     }
//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<?> deleteUser(@PathVariable("id") long id)  {
//         try{
//             userRebository.deleteById(id);
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);      
//         }catch(Exception e){
//             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }
    
// }
