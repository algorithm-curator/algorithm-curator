/* eslint-disable @typescript-eslint/no-unused-vars */
import axios from "axios";

const axiosInstance = axios.create({
	baseURL: process.env.REACT_APP_API_SERVER,
	timeout: 30000,
	withCredentials: true,

	headers: {
		"Cache-Control": "no-cache",
	},
});

// server로 보내기 직전의 정보 intercept
axiosInstance.interceptors.request.use(
	(config) => {
		return config;
	},
	(err) => {
		/* 
    request를 보낼 때에 error 발생 경우, 여기서 catch 가능
    */
		return Promise.reject(err);
	}
);

// 서버에서 받아 온 response를 client에서 보기 직전
axiosInstance.interceptors.response.use(
	(config) => {
		/*
    요청을 보낸 후, response가 오는 경우 여기서 먼저 확인 가능
    * 활용 *
    1. status-code가 정상이어도 내용상 이유로 에러 처리하고 싶은 경우
    2. 민감 정보에 대한 데이터 가공
    */
		return config;
	},
	({ config, request, response, ...err }) => {
		/*
    response 응답 후에 status code가 40x 50x  처럼 에러 나타나는 경우에 해당 루트 수행
   */
		// eslint-disable-next-line prefer-promise-reject-errors
		return Promise.reject({ config, response, ...err });
	}
);

export default axiosInstance;
