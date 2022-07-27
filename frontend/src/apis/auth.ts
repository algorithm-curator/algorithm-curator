import axiosInstance from "Utils/axiosInstance";

export const getLogin = (accessToken: string | null) => {
	return axiosInstance.post("/api/auth/login", { access_token: accessToken });
};

export const getLogout = (
	apiKey: string | null,
	accessToken: string | null
) => {
	return axiosInstance.post(
		"/api/auth/logout",
		{ access_token: accessToken },
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};
