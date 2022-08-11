import styled from "@emotion/styled";

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	padding: 1rem;
`;

export const ProblemNav = styled.ul`
	display: flex;
	justify-content: space-between;
	background-color: white;
	border-radius: 1rem;
	padding: 0 1rem;
`;

export const Status = styled.li`
	color: black;
	font-weight: 500;
	font-size: 1.5rem;
	width: max-content;
	margin: 1rem;
	cursor: pointer;
	:hover {
		text-decoration: underline;
	}
`;
