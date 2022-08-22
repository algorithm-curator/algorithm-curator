/* eslint-disable @typescript-eslint/no-unused-vars */

import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { isLoggedState } from "stores/Auth";
import { getMyProfile } from "apis/user";
import { API_TOKEN, KAKAO_ACCESS_TOKEN } from "../localStorageKeys";

export const myprofile = async () => {
	const apiToken = localStorage.getItem(API_TOKEN);
	const navigate = useNavigate();
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	if (apiToken) {
		await getMyProfile(apiToken)
			.then((res) => {
				if (!res.data.response.nickname) {
					navigate("/mypage");
					alert("닉네임을 설정해주세요");
				} else {
					setIsLogged(true);
				}
			})
			.catch((err) => {
				alert("에러가 발생했습니다.");
			});
	} else {
		alert("로그인 해주세요.");
		localStorage.removeItem(API_TOKEN);
		localStorage.removeItem(KAKAO_ACCESS_TOKEN);
		setIsLogged(false);
	}
};
