//package com.example.oauth2Jwt.api.controller.auth;
//
//import com.example.oauth2Jwt.oauth.entity.UserPrincipal;
//import com.example.oauth2Jwt.oauth.info.OAuth2UserInfo;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("home")
//public class HomeController {
////    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
////    private final UserPrincipal userInfo;
////
////    public HomeController(UserPrincipal userInfo) {
////        this.userInfo = userInfo;
////    }
////    private final AuthenticationFacade authFacade; //로그인한 사용자 정보를 가져오기 위해
////
////    public HomeController(
////            @Autowired AuthenticationFacade authFacade //로그인한 사용자 정보를 가져오기 위해
////    ) {
////        this.authFacade = authFacade; //로그인한 사용자 정보를 가져오기 위해
////    }
//
//    /*
//    * Principal 인터페이스를 사용하여 사용자의 정보를 담을수 있다.
//    * Principal principal 은 로그인한 대상을 확인할수 있다.
//    *
//    * Authentication authentication는 로그인 뿐만 아니라 여러 로그인방식에 따른 인터페이스이다.
//    *
//    * SecurityContextHolder는 현재 쓰레드와 관련된 보안 정보는 SecurityContext에 저장된다.
//    * SecurityContextHolder 클래스를 통해 getContext()를 받아온다.
//    * SecurityContextHolder는 자신이 실행되고 있는 쓰레드 기준으로 실행된다.
//    * */
//    @GetMapping
//    public String home(){
//        try{
////            logger.info("connected user: {}", principal.getName());
////            logger.info("connected user: {}", authentication.getName());
////            logger.info("connected user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
//            logger.info("connected user: {}",
//                    userInfo.getName());
//        }catch (NullPointerException e){
//            logger.info("no user logged in");
//        }
//
//        return "index";
//    }
//
//}
