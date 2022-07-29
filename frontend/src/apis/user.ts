import axiosInstance from "Utils/axiosInstance";

export const checkNickname = (apiKey: string | null, name: string) => {
	return axiosInstance.get(`/api/users/nickname?name=${name}`, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};

export const getMyProfile = (apiKey: string | null) => {
	return axiosInstance.get(`/api/users`, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};

export const join = (accessToken: string) => {
	return axiosInstance.post(`/api/users/join`, { access_token: accessToken });
};

export const putMyProfile = (
	apiKey: string | null,
	nickname: string,
	profileImage?: string
) => {
	return axiosInstance.put(
		`/api/users`,
		{
			nickname,
			profile_image: profileImage,
		},
		{ headers: { api_key: `Bearer ${apiKey}` } }
	);
};
