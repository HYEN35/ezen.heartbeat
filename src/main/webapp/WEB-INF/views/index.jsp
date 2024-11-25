<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>HeartBeat</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.1,maximum-scale=1,user-scalable=yes,viewport-fit=cover">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="title" content="HeartBeat">
	<meta name="description" content="당신의 심장을 뛰게 하는 음악 사이트, It makes your HeartBeat">
	<meta name="keywords" content="HeartBeat,음악스트리밍,음악검색,음악듣기,팬커뮤니티,팬소통,팬소통앱,팬커뮤니티소통앱">
	<meta name="og:title" content="HeartBeat">
	<meta name="og:url" content="heartbeat.kr/">
	<meta name="og:site_name" content="HeartBeat">
	<meta name="og:description" content="당신의 심장을 뛰게 하는 음악 사이트, It makes your HeartBeat">
	<meta name="og:image" content="#none">
	<link rel="shortcut icon" type="image/x-icon" href="./img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/vendor/jquery-2.9.7-fullpage.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="inner main">
		<div class="header">
			<div class="headerWrap">
				<h1><a href="${pageContext.request.contextPath}/main"></a></h1>
				<a href="${pageContext.request.contextPath}/login" class="btn-full en">Sign In</a>
			</div>
		</div>		
		<div class="scroll">
			<span></span>
			<span></span>
			<span></span>
		</div>
		<div class="container">
			<ul id="menu" class="fullNav">
				<li data-menuanchor="section-intro" class="active"><a href="#section-intro"></a></li>
				<li data-menuanchor="section-makes"><a href="#section-makes"></a></li>
				<li data-menuanchor="section-hashtag"><a href="#section-hashtag"></a></li>
				<li data-menuanchor="section-community"><a href="#section-community"></a></li>
				<li data-menuanchor="section-subscribe"><a href="#section-subscribe"></a></li>
				<li data-menuanchor="section-connection"><a href="#section-connection"></a></li>
			</ul>
			<div id="fullpage" class="fullpage">
				<div class="section section-intro">
					<div class="video">
						<video src="${pageContext.request.contextPath}/video/newjeans.mp4" muted autoplay loop data-keepplaying playsinline></video>
					</div>
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en">HeartBeat</div>
								<div class="subTitle">당신의 심장을 뛰게 하는 <br class="mob">팬 커뮤니티 플랫폼</div>
							</div>
						</div>
					</div>
				</div>
				<div class="section section-makes">
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en">It makes <br class="web">your <br><i class="c-main">HeartBeat</i></div>
								<div class="subTitle">음악과 소통을 동시에 하는 <br class="mob">HeartBeat <br>나만의 플레이리스트를 만들고 <br>나만의 아티스트와 소통하세요.</div>
							</div>
							<div class="imgBx"></div>
						</div>
					</div>
				</div>
				<div class="section section-hashtag">
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en">Make your <br>Playlist <br class="web"> with <br class="web"><i class="c-main">HASHTAG</i></div>
								<div class="subTitle">원하는 분위기와 감성을 선택하면 <br>맞춤형 음악이 <br class="web">끊임없이 <br class="mob">재생되어 당신의 귀를 사로잡습니다.</div>
							</div>
							<div class="imgBx"></div>
						</div>
					</div>
				</div>
				<div class="section section-community">
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en">No. 1 <br>Community <br>Music Site <br><i class="c-main">HeartBeat</i></div>
								<div class="subTitle">귀로만 즐기는 것은 그만 <br>음악을 좋아하는 사람들과 소통 할 수 있는 <br>음악 커뮤니티 지금 시작하세요.</div>
							</div>
							<div class="imgBx"></div>
						</div>
					</div>
				</div>   
				<div class="section section-subscribe">
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en"><i class="c-main">Subscribe</i> <br>Service</div>
								<div class="subTitle">나의 아티스트와 소통할 수 있는 <br class="mob">단 하나의 뮤직앱 <br>팬덤 커뮤니티에서 아티스트와 함께 소통하며 <br class="mob">음악을 즐겨보세요.</div>
							</div>
							<div class="imgBx"></div>
						</div>
					</div>
				</div>   
				<div class="section section-connection">
					<div class="sectionWrap">
						<div class="contents">
							<div class="txtBx">
								<div class="title en">Start the <br><i class="c-main">HeartBeat</i></div>
								<div class="subTitle">음악만을 위해 만들어진 앱 <br>지금 하트비트를 사용해 보세요.</div>
								<a href="${pageContext.request.contextPath}/login" class="btn-full en">Subscribe to Membership</a>
							</div>
							<div class="imgBx"></div>
						</div>
					</div>
				</div>   
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/vendor/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/jquery-2.9.7-fullpage.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/ScrollMagic.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/debug.addIndicators.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/TweenMax.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/animation.gsap.js"></script>
	<script>
		$(function(){
			fullPage();
			scrollMagic();
		});

		function fullPage(){
			$('#fullpage').fullpage({
				anchors: ['section-intro', 'section-makes','section-hashtag','section-community','section-subscribe','section-connection'],
				menu: '#menu',
				navigation: false,
				scrollingSpeed: 1000,
				afterLoad: function(anchor, index){
					if(index == 1){
						$('#menu').fadeOut(100);
					}else{
						$('#menu').fadeIn(100);   
					}
					if(index == 6){
						$('.scroll').hide();
					}
					else{
						$('.scroll').show();
					}
				}
			});
		};

		//https://nykim.work/30
		function scrollMagic() {
			var controller = new ScrollMagic.Controller();

			//intro
			var tween_intro = TweenMax.staggerFrom('.section-intro .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_intro = new ScrollMagic.Scene({
				triggerElement: '.section-intro',

			})
			.setTween(tween_intro)
			.addTo(controller)

			var tween_intro = TweenMax.staggerFrom('.section-intro .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.7
			}, 0.2);
			var sub_tween_intro = new ScrollMagic.Scene({
				triggerElement: '.section-intro',

			})
			.setTween(tween_intro)
			.addTo(controller)

			//makes
			var tween_makes = TweenMax.staggerFrom('.section-makes .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_makes = new ScrollMagic.Scene({
				triggerElement: '.section-makes .title',

			})
			.setTween(tween_makes)
			.addTo(controller)

			var tween_makes = TweenMax.staggerFrom('.section-makes .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.6
			}, 0.2);
			var sub_tween_makes = new ScrollMagic.Scene({
				triggerElement: '.section-makes',

			})
			.setTween(tween_makes)
			.addTo(controller)

			var tween_makes = TweenMax.staggerFrom('.section-makes .imgBx', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.9
			}, 0.2);
			var sub_tween_makes = new ScrollMagic.Scene({
				triggerElement: '.section-makes',

			})
			.setTween(tween_makes)
			.addTo(controller)

			//hashtag
			var tween_hashtag = TweenMax.staggerFrom('.section-hashtag .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_hashtag = new ScrollMagic.Scene({
				triggerElement: '.section-hashtag',

			})
			.setTween(tween_hashtag)
			.addTo(controller)
			
			var tween_hashtag = TweenMax.staggerFrom('.section-hashtag .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.6
			}, 0.2);
			var sub_tween_hashtag = new ScrollMagic.Scene({
				triggerElement: '.section-hashtag',

			})
			.setTween(tween_hashtag)
			.addTo(controller)
			
			var tween_hashtag = TweenMax.staggerFrom('.section-hashtag .imgBx', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.9
			}, 0.2);
			var sub_tween_hashtag = new ScrollMagic.Scene({
				triggerElement: '.section-hashtag',

			})
			.setTween(tween_hashtag)
			.addTo(controller)

			//community
			var tween_community = TweenMax.staggerFrom('.section-community .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_community = new ScrollMagic.Scene({
				triggerElement: '.section-community',

			})
			.setTween(tween_community)
			.addTo(controller)
			
			var tween_community = TweenMax.staggerFrom('.section-community .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.6
			}, 0.2);
			var sub_tween_community = new ScrollMagic.Scene({
				triggerElement: '.section-community',

			})
			.setTween(tween_community)
			.addTo(controller)
			
			var tween_community = TweenMax.staggerFrom('.section-community .imgBx', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.9
			}, 0.2);
			var sub_tween_community = new ScrollMagic.Scene({
				triggerElement: '.section-community',

			})
			.setTween(tween_community)
			.addTo(controller)

			//subscribe
			var tween_subscribe = TweenMax.staggerFrom('.section-subscribe .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_subscribe = new ScrollMagic.Scene({
				triggerElement: '.section-subscribe',

			})
			.setTween(tween_subscribe)
			.addTo(controller)
			
			var tween_subscribe = TweenMax.staggerFrom('.section-subscribe .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.6
			}, 0.2);
			var sub_tween_subscribe = new ScrollMagic.Scene({
				triggerElement: '.section-subscribe',

			})
			.setTween(tween_subscribe)
			.addTo(controller)
			
			var tween_subscribe = TweenMax.staggerFrom('.section-subscribe .imgBx', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.9
			}, 0.2);
			var sub_tween_subscribe = new ScrollMagic.Scene({
				triggerElement: '.section-subscribe',

			})
			.setTween(tween_subscribe)
			.addTo(controller)

			//connection
			var tween_connection = TweenMax.staggerFrom('.section-connection .title', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.3
			}, 0.2);
			var sub_tween_connection = new ScrollMagic.Scene({
				triggerElement: '.section-connection',

			})
			.setTween(tween_connection)
			.addTo(controller)

			var tween_connection = TweenMax.staggerFrom('.section-connection .subTitle', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.6
			}, 0.2);
			var sub_tween_connection = new ScrollMagic.Scene({
				triggerElement: '.section-connection',

			})
			.setTween(tween_connection)
			.addTo(controller)

			var tween_connection = TweenMax.staggerFrom('.section-connection .btn-full', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 0.9
			}, 0.2);
			var sub_tween_connection = new ScrollMagic.Scene({
				triggerElement: '.section-connection',

			})
			.setTween(tween_connection)
			.addTo(controller)

			.setTween(tween_connection)
			.addTo(controller)

			var tween_connection = TweenMax.staggerFrom('.section-connection .imgBx', 1.0, {
				ease: Sine.easeOut,
				opacity: 0,
				y: 80,
				delay: 1.2
			}, 0.2);
			var sub_tween_connection = new ScrollMagic.Scene({
				triggerElement: '.section-connection',

			})
			.setTween(tween_connection)
			.addTo(controller)
			// .addIndicators({
			// 	name: "connection"
			// })
		}
	</script>
</body>
</html>