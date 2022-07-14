import styled from "@emotion/styled";
import { Link } from "react-router-dom";

export const Container = styled.div`
	margin: auto;
	padding-top: 7rem;
`;

export const ProblemListTextWrapper = styled.div`
	display: flex;
	flex-direction: row-reverse;
	align-items: center;
	width: 70%;
	margin: 2rem auto;
`;

export const ProblemListText = styled(Link)`
	background-color: transparent;
	color: #65cc35;
	text-decoration: none;
	font-weight: 500;
	cursor: pointer;
`;

export const ProblemStatusButton = styled.button`
	font-weight: 400;
	font-size: 1.05rem;
	background-color: rgba(255, 255, 255, 0.2);
	color: white;
	padding: 0.5rem 1.5rem;
	margin-left: 1rem;
	border-radius: 1rem;
	border: none;
	cursor: pointer;
`;
