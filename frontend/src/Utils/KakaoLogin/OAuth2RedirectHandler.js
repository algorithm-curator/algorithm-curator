/* eslint-disable @typescript-eslint/no-unused-vars */
import axios from "axios";
import React, { useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { isLoggedState } from "stores/Auth";
import { getLogin } from "apis/auth";
import { getMyProfile, join } from "apis/user";
import ReactLoading from "react-loading";
import { KAKAO_AUTH_TOKEN_URL } from "./OAuth";
import { KAKAO_ACCESS_TOKEN, API_TOKEN } from "../localStorageKeys";

// 추가적으로 해야할 작업
function OAuth2RedirectHandler() {
	const [searchParams, setSearchParams] = useSearchParams();
	const navigate = useNavigate();
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);

	useEffect(() => {
		const code = searchParams.get("code");
		const getTokenURL = `${KAKAO_AUTH_TOKEN_URL}code=${code}&grant_type=authorization_code`;

		// 카카오에서 엑세스 토큰 얻어오기
		const getToken = async () => {
			await axios({
				method: "post",
				url: getTokenURL,
				headers: {
					"content-type": "application/x-www-form-urlencoded;charset=utf-8",
				},
			})
				// 정상적으로 받아올 경우
				.then((res) => {
					localStorage.setItem(KAKAO_ACCESS_TOKEN, res.data.access_token);
					const tryLogin = async () => {
						// 로그인 시도하기
						await getLogin(res.data.access_token)
							// 로그인 성공시 2가지 경우
							.then((res) => {
								localStorage.setItem(API_TOKEN, res.data.response.api_token);
								// !!! 바로 아래 myprofile 로직은 바꿔야할 필요가 있다
								const myprofile = async () => {
									await getMyProfile(res.data.response.api_token)
										// 1. 닉네임이 없는 경우 mypage 이동
										// 2. 닉네임 있는 경우는 landingPage 이동 -> 기존 있던 페이지로 이동하게 할 필요가 있다.
										.then((res) => {
											if (!res.data.response.nickname) navigate("/mypage");
											else {
												setIsLogged(true);
												navigate("/");
											}
										})
										.catch((err) => {
											console.log(err);
										});
								};
								myprofile();
							})
							// 로그인 실패시 회원가입하고 로그인
							.catch((err) => {
								if (err.data.status === 401) {
									const getJoin = async () => {
										await join(res.data.access_token)
											.then((res) => {
												navigate("/mypage");
											})
											.catch((err) => {
												console.log(err);
											});
									};
									getJoin();
								}
							});
					};
					tryLogin();
				})
				// 에러가 있을 경우
				.catch((err) => console.log(err));
		};

		getToken();
	}, []);

	return (
		<div
			style={{
				display: "flex",
				justifyContent: "center",
				alignItems: "center",
				width: "100wh",
				height: "90vh",
			}}
		>
			<ReactLoading type="spin" width="5%" />
		</div>
	);
}

export default OAuth2RedirectHandler;
