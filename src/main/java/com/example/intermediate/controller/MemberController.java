package com.example.intermediate.controller;

import com.example.intermediate.controller.request.LoginRequestDto;
import com.example.intermediate.controller.request.MemberRequestDto;
import com.example.intermediate.controller.request.NameRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.KakaoUserService;
import com.example.intermediate.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;
  private final KakaoUserService kakaoUserService;

  @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }

  @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }

  @RequestMapping(value = "/api/auth/member/mypage",method = RequestMethod.GET)
  public ResponseDto<?> myPage(HttpServletRequest request){
    return memberService.myPage(request);
  }

  @RequestMapping(value = "/api/auth/member/name", method = RequestMethod.POST)
  public ResponseDto<?> updateName(@RequestBody NameRequestDto requestDto, HttpServletRequest request) {
    return memberService.updateName(requestDto, request);
  }


  @GetMapping("/user/kakao/callback")
  public ResponseDto<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//    try { // 회원가입 진행 성공시
    return kakaoUserService.kakaoLogin(code, response);
//    } catch (Exception e) { // 에러나면 false
//      throw new CustomException(ErrorCode.INVALID_KAKAO_LOGIN_ATTEMPT);
  }

}
