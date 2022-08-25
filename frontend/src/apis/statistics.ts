import axiosInstance from "Utils/axiosInstance";

export const getSolvedCount = (apiKey: string | null) => {
	return axiosInstance.get(`/api/statistics/solved-count`, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};

export const getSolvedRate = (apiKey: string | null) => {
	return axiosInstance.get(`/api/statistics/solved-rate`, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};

export const getSolvedTrace = (apiKey: string | null, date?: string | null) => {
	return axiosInstance.get(
		`/api/statistics/solved-trace${date ? `?date=${date}` : ""}`,
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};
