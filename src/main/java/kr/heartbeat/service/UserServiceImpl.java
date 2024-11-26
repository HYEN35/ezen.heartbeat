package kr.heartbeat.service;

import java.security.SecureRandom;
import java.util.Date;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
>>>>>>> 9373f26 (파일 전체 업로드)
=======
>>>>>>> e47ac89 (Revert "병합 후")
=======
>>>>>>> parent of 0097fe9 (병합 후)
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.heartbeat.persistence.UserPersistenceImpl;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Service
@Transactional 
public class UserServiceImpl implements UserService {
<<<<<<< HEAD
<<<<<<< HEAD

	@Inject
	private UserPersistenceImpl userPersistenceImpl;

	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
=======
	
=======

>>>>>>> e47ac89 (Revert "병합 후")
	@Inject
	private UserPersistenceImpl userPersistenceImpl;

	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
<<<<<<< HEAD
	
>>>>>>> 9373f26 (파일 전체 업로드)
=======
>>>>>>> e47ac89 (Revert "병합 후")
	//중복체크
	@Override
	public UserVO idCheck(String email) {
		return userPersistenceImpl.idCheck(email);
	}
	@Override
	public UserVO phoneCheck(String phone) {
		return userPersistenceImpl.phoneCheck(phone);
	}
	@Override
	public UserVO nicknameCheck(String nickname) {
		return userPersistenceImpl.nicknameCheck(nickname);
	}
	//회원가입
	@Override
	public int insertUser(UserVO userVO) {
		System.out.println("========== Service member email(id) : "+userVO.getEmail());
		return userPersistenceImpl.insertUser(userVO);
	}
	//회원가입 시 유저 역할 추가
	@Override
	public int insertUserRole(String email) {
		return userPersistenceImpl.insertUserRole(email);
	}
<<<<<<< HEAD
<<<<<<< HEAD

=======
	
>>>>>>> 9373f26 (파일 전체 업로드)
=======

>>>>>>> e47ac89 (Revert "병합 후")
	//로그인
	@Override
	public UserVO login(UserVO userVO) {
		System.out.println("========== 로그인 Service member email(id) : "+userVO.getEmail());
		return userPersistenceImpl.login(userVO);
	}
	//아이디찾기
	@Override
	public UserVO findId(UserVO userVO) {
		System.out.println("=====================Service name: "+ userVO.getName());
		return userPersistenceImpl.findId(userVO);
	}
	//비밀번호 찾기 - 메일 전송 버전
	@Override
	public int searchPwd(UserVO userVO) {

		int result = 0;
		String email = userVO.getEmail();
<<<<<<< HEAD
<<<<<<< HEAD

		UserVO uvo = userPersistenceImpl.searchPwd(userVO);//사용자가 입력한 값이 맞는지 확인한다.

		if (uvo != null) { //사용자가 입력한 값이 db에 존재하면 uvo도 값이 존재한다.
			String newPassword = createNewPassword(); //난수로 새 비밀번호 생성
			result = userPersistenceImpl.updatePwd(email, newPassword); //새로운 비밀번호 db에 저장

=======
		
=======

>>>>>>> e47ac89 (Revert "병합 후")
		UserVO uvo = userPersistenceImpl.searchPwd(userVO);//사용자가 입력한 값이 맞는지 확인한다.

		if (uvo != null) { //사용자가 입력한 값이 db에 존재하면 uvo도 값이 존재한다.
			String newPassword = createNewPassword(); //난수로 새 비밀번호 생성
			result = userPersistenceImpl.updatePwd(email, newPassword); //새로운 비밀번호 db에 저장
<<<<<<< HEAD
			
>>>>>>> 9373f26 (파일 전체 업로드)
=======

>>>>>>> e47ac89 (Revert "병합 후")
			if (result > 0) //새로운 비밀번호가 db에 저장되면 메일로 새로운 비밀번호 발송
				sendNewPasswordByMail(email, newPassword, userVO);
		}
		return result;
	}
<<<<<<< HEAD
<<<<<<< HEAD

	private String createNewPassword() { //난수로 새 비밀번호 생성
		System.out.println("[AdminMemberService] createNewPassword()");

		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z'
		};
=======
	
=======

>>>>>>> e47ac89 (Revert "병합 후")
	private String createNewPassword() { //난수로 새 비밀번호 생성
		System.out.println("[AdminMemberService] createNewPassword()");

		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z'
<<<<<<< HEAD
				};
>>>>>>> 9373f26 (파일 전체 업로드)
=======
		};
>>>>>>> e47ac89 (Revert "병합 후")

		StringBuffer stringBuffer = new StringBuffer();
		// Random보다 강력한 난수 생성( SecureRandom는 Random을 상속)
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
<<<<<<< HEAD
<<<<<<< HEAD

=======
		
>>>>>>> 9373f26 (파일 전체 업로드)
=======

>>>>>>> e47ac89 (Revert "병합 후")
		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
<<<<<<< HEAD
<<<<<<< HEAD

			if (index % 2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());

		}

		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());

		return stringBuffer.toString(); //새 비밀번호

	}

	private void sendNewPasswordByMail(String toMailAddr, String newPassword, UserVO userVO) {
		System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		System.out.println("============이메일 주소 : "+userVO.getEmail());

		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

=======
		
			if (index % 2 == 0) 
=======

			if (index % 2 == 0)
>>>>>>> e47ac89 (Revert "병합 후")
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());

		}

		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());

		return stringBuffer.toString(); //새 비밀번호

	}

	private void sendNewPasswordByMail(String toMailAddr, String newPassword, UserVO userVO) {
		System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		System.out.println("============이메일 주소 : "+userVO.getEmail());

		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
<<<<<<< HEAD
				
>>>>>>> 9373f26 (파일 전체 업로드)
=======

>>>>>>> e47ac89 (Revert "병합 후")
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(userVO.getEmail()); //받는 메일의 주소
//				mimeMessageHelper.setTo(toMailAddr);
				mimeMessageHelper.setSubject("[Heartbeat] 새 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true);
<<<<<<< HEAD
<<<<<<< HEAD

			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);

	}


=======
				
=======

>>>>>>> e47ac89 (Revert "병합 후")
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);

	}
<<<<<<< HEAD
	
	
>>>>>>> 9373f26 (파일 전체 업로드)
=======


>>>>>>> e47ac89 (Revert "병합 후")
	//회원수정
	@Override
	public void modify(String newPwd, UserVO userVO) {
		System.out.println("========== 로그인 Service member newPwd : "+newPwd);
		System.out.println("========== 로그인 Service member getNickname : "+userVO.getNickname());
		userPersistenceImpl.modify(newPwd,userVO);
	}
	//멤버쉽 수정(level)
	@Override
	public void membership(UserVO userVO) {
		userPersistenceImpl.membership(userVO);
	}
	//회원 탈퇴
	@Override
	public void delete(UserVO uvo) {
		userPersistenceImpl.delete(uvo);
	}
<<<<<<< HEAD
<<<<<<< HEAD


=======
	
	
>>>>>>> 9373f26 (파일 전체 업로드)
=======


>>>>>>> e47ac89 (Revert "병합 후")
	@Override
	public UserroleVO role(UserroleVO userrolevo) {
		System.out.println("=============서비스role : "+userrolevo.getRole_id());
		return userPersistenceImpl.role(userrolevo);
	}


<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD



=======
	
	

	
>>>>>>> 9373f26 (파일 전체 업로드)
=======



>>>>>>> e47ac89 (Revert "병합 후")

=======




>>>>>>> parent of 0097fe9 (병합 후)
}
