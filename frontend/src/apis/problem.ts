import axiosInstance from "Utils/axiosInstance";

export const getTodayProblems = (apiKey: string | null) => {
	return axiosInstance.post("/api/problems/", null, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};

export const getListProblems = (
	apiKey: string | null,
	state: number | null,
	page: number | null
) => {
	return axiosInstance.get(
		`/api/problems/?${state ? `state=${state}&` : ""}${
			Number.isInteger(page) ? `page=${page}&size=10` : ""
		}`,
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};

export const putProblemStatus = (
	apiKey: string | null,
	id: number | null,
	state: number | null
) => {
	return axiosInstance.put(
		"/api/problems/",
		{ problems: [{ id, state }] },
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};

export const putProblemsStatus = (apiKey: string | null, problems: any) => {
	return axiosInstance.put(
		"/api/problems/",
		{ problems },
		{
			headers: { api_key: `Bearer ${apiKey}` },
		}
	);
};

export const deleteProblems = (apiKey: string | null, id: number | null) => {
	axiosInstance.delete(`/api/problems/${id}`, {
		headers: { api_key: `Bearer ${apiKey}` },
	});
};
