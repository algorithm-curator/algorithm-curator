import React, { useRef } from "react";
import {
	Container,
	DeduplicationCheck,
	ProfileEditSubmit,
	ProfileImage,
	ProfileWrapper,
	Title,
} from "./styles";

function MyPage() {
	const imageInput = useRef<HTMLInputElement>(null);

	const onClickImage = () => {
		if (imageInput.current) imageInput.current.click();
	};

	return (
		<Container>
			<Title>내 프로필</Title>
			<ProfileWrapper>
				<ProfileImage
					srcUrl={`${`${`${process.env.PUBLIC_URL}DefaultProfileImage.png`}`}`}
					onClick={onClickImage}
				>
					<input type="file" style={{ display: "none " }} ref={imageInput} />
				</ProfileImage>
				<div
					style={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
						marginTop: "2rem",
					}}
				>
					<input
						style={{ border: "none", fontSize: "1.7rem", marginTop: "auto" }}
						type="text"
						placeholder="내닉네임"
					/>
					<DeduplicationCheck>중복체크</DeduplicationCheck>
				</div>
				<ProfileEditSubmit>수정 완료</ProfileEditSubmit>
			</ProfileWrapper>
		</Container>
	);
}

export default MyPage;
