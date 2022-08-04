import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	background-color: #c4c4c4;
	padding: 0.7rem;
	border-radius: 3rem;
	width: 95%;
	margin: 0.5rem auto;
`;

export const Title = styled.h2`
	margin-left: 2rem;
	font-size: 2rem;
	font-weight: 600;
`;

export const SolveLevelWrapper = styled.div`
	display: flex;
`;

export const Level = styled.div`
	font-size: 1rem;
	background-color: #6fcf97;
	margin: 0 1rem;
	padding: 0.5rem 1rem;
	border-radius: 1rem;
`;

export const Origin = styled.div`
	font-size: 1rem;
	background-color: #f2c94c;
	margin: 0 1rem;
	padding: 0.5rem 1rem;
	border-radius: 1rem;
`;

export const ChangeStatus = styled.div`
	font-size: 1rem;
	background-color: skyblue;
	margin: 0 1rem;
	padding: 0.5rem 1rem;
	border-radius: 1rem;
	cursor: pointer;
	:hover {
		font-weight: 600;
	}
`;

export const StatusSelect = styled.select`
	background-color: #e0e0e0;
	border: none;
	option {
	}
`;

export const CompleteButton = styled.button`
	font-size: 1rem;
	background-color: skyblue;
	margin: 0 1rem;
	padding: 0.5rem 1rem;
	border: none;
	border-radius: 1rem;
	cursor: pointer;
	:hover {
		font-weight: 600;
	}
`;
