import React, { useState, useEffect, useRef } from "react";
import { useRecoilState } from "recoil";
import { isLoggedState } from "stores/Auth";
import { checkNickname, getMyProfile, putMyProfile } from "apis/user";
import { API_TOKEN } from "Utils/localStorageKeys";
import axios from "axios";
import {
	Container,
	NicknameCheck,
	ProfileEditSubmit,
	ProfileImage,
	ProfileWrapper,
	Title,
	LimitTextBox,
} from "./styles";

// 2. 헤더 변경을 헤아됨. -> 이거는 상태 관리로 되는지 생각해보자
//  2-1. 초기 로그인 시, 헤더는 비로그인상태 헤더로
//  2-2. 프로필 수정 버튼 시, 헤더는 로그인상태 헤더로
// * process.env.PUBLIC_URL -> 빈 문자열 출력되는 현상
function MyPage() {
	const imageInput = useRef<HTMLInputElement>(null);
	const [nicknameText, setNicknameText] = useState<string>("");
	const [nicknameCheckState, setNicknameCheckState] = useState<boolean>(false);
	const [limitNicknameText, setLimitNicknameText] = useState<string>("");
	const [imageObject, setImageObject] = useState<any>({
		file: "",
		fileName: "",
	});
	const [previewImage, setPreviewImage] = useState<any>("");
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	const apiToken = localStorage.getItem(API_TOKEN);
	const onClickImage = () => {
		if (imageInput.current) imageInput.current.click();
	};
	const onClickNicknameCheck = () => {
		const getCheckNickname = async () => {
			await checkNickname(apiToken, nicknameText)
				.then((res) => {
					// API 변경 요청 -> response에 닉네임 담아서, 이유는 기존 닉네임 중복 체크 시 로직 까다롭지않게
					if (res.status === 401) {
						setIsLogged(false);
						alert("로그인 토큰이 만료되었습니다. 다시 로그인 해주세요.");
					} else if (res.data.response.result) {
						alert("사용가능한 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					} else {
						alert("중복된 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					}
				})
				.catch((err) => console.log(err));
		};
		getCheckNickname();
	};
	const limitNickname = (name: string) => {
		let status = "";
		if (name.length < 3 || name.length > 17) {
			status = "닉네임은 2자 이상 16자 이하로 가능합니다.";
		} else if (name.search(/\s/) !== -1) {
			status = "닉네임에 빈 칸을 포함 할 수 없습니다.";
		}
		// 사용가능하다면 false 리턴
		return status;
	};
	const onChangeImage = (e: any) => {
		const reader = new FileReader();
		const file = e.target.files![0];
		reader.onloadend = () => {
			setImageObject({
				file,
				fileName: file.name,
			});
			setPreviewImage(reader.result);
			console.log(reader.result, file.name);
		};
		reader.readAsDataURL(file);
	};
	const onClickEditSubmit = () => {
		if (nicknameCheckState && !limitNickname(nicknameText)) {
			const tryPutMyProfile = async () => {
				await putMyProfile(apiToken, nicknameText, imageObject.fileName)
					.then((res) => {
						// 헤더 상태변경하면됨
						if (res.status === 401) {
							setIsLogged(false);
							alert("로그인 토큰이 만료되었습니다. 다시 로그인 해주세요.");
						} else {
							axios
								.put(res.data.response.profile_image, imageObject.file)
								.then((res) => {
									if (res.status === 200) {
										alert("프로필이 수정되었습니다.");
										setIsLogged(true);
									}
								})
								.catch((err) => console.log(err));
						}
					})
					.catch((err) => {
						console.log(err);
					});
			};
			tryPutMyProfile();
		} else {
			alert("닉네임을 확인해주세요.");
		}
	};

	useEffect(() => {
		(async () => {
			await getMyProfile(apiToken)
				.then((res) => {
					if (res.status === 401) {
						setIsLogged(false);
						alert("로그인 토큰이 만료되었습니다. 다시 로그인 해주세요.");
					} else {
						const nicknameTemp = res.data.response.nickname;
						const UrlTemp = res.data.response.profile_image;
						setNicknameText(nicknameTemp);
						limitNickname(nicknameTemp);
						setPreviewImage(UrlTemp);
						setImageObject({ ...imageObject, fileName: UrlTemp });
					}
				})
				.catch((err) => {
					console.log(err);
				});
		})();
	}, []);

	return (
		<Container>
			<Title>내 프로필</Title>
			<ProfileWrapper>
				<ProfileImage
					srcUrl={previewImage || "/DefaultProfileImage.png"}
					onClick={onClickImage}
				>
					<input
						type="file"
						accept="image/jpg, image/jpeg, image/png"
						style={{ display: "none " }}
						ref={imageInput}
						onChange={(e) => onChangeImage(e)}
					/>
				</ProfileImage>
				<div>
					<div
						style={{
							display: "flex",
							justifyContent: "center",
							alignItems: "center",
							marginTop: "2rem",
						}}
					>
						<input
							style={{
								border: "none",
								fontSize: "1.3rem",
								marginTop: "auto",
								padding: "0.5rem 1rem",
								borderColor: limitNicknameText ? "red" : "green",
								borderStyle: "solid",
								borderWidth: "1px",
							}}
							type="text"
							placeholder="닉네임을 입력하세요."
							value={nicknameText}
							onChange={(event) => {
								setNicknameText(event.target.value);
								setLimitNicknameText(limitNickname(event.target.value));
							}}
							maxLength={20}
						/>
						<NicknameCheck onClick={onClickNicknameCheck}>
							중복체크
						</NicknameCheck>
					</div>
					<LimitTextBox>{limitNicknameText}</LimitTextBox>
				</div>
				<ProfileEditSubmit onClick={onClickEditSubmit}>
					수정 완료
				</ProfileEditSubmit>
			</ProfileWrapper>
		</Container>
	);
}

export default MyPage;
