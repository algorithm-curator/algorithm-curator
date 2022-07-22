import axiosInstance from "utils/axiosInstance";

export const getLogin = (accessToken: string) => {
	return axiosInstance.post("/api/auth/login", { access_token: accessToken });
};

export const getLogout = (accessToken: string) => {
	return axiosInstance.post("/api/auth/logout", { access_token: accessToken });
};
