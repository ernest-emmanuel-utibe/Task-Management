//package com.api.taskManagement.controllers;
//public class UserController {
//    @Autowired
//    private UserService service;
//
//    final String clientUrl = Client.clientUrl;
//
//    @GetMapping("/getAllUser")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserListResource> getAllUser() {
//        UserListResource ulr = new UserListResource();
//        ulr.setUserList(service.getAllUser());
//        return new ResponseEntity<UserListResource>(ulr, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllUser/{pid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserListResource> getAllUser(@PathVariable("pid") Long pid) {
//        UserListResource ulr = new UserListResource();
//        ulr.setUserList(service.getAllUserByProgram(pid));
//        return new ResponseEntity<UserListResource>(ulr, HttpStatus.OK);
//    }
//
//    @GetMapping("/getUser/{uid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<UserResource> getUserById(@PathVariable("uid") Long uid) {
//        UserResource ur = new UserResource();
//        BeanUtils.copyProperties(service.getUser(uid), ur);
//        return new ResponseEntity<UserResource>(ur, HttpStatus.OK);
//    }
//}
