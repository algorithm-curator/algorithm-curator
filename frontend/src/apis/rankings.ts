import axiosInstance from "Utils/axiosInstance";

export const getRankings = (apiKey: string | null, page: number | null) => {
	return axiosInstance.get(
		`/api/rankings?${Number.isInteger(page) ? `page=${page}&size=10` : ""}`,
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};
