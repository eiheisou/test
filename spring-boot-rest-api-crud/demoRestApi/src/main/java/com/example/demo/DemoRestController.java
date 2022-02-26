package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API定義クラス
 */
@RestController
public class DemoRestController {

	/** ユーザーデータテーブル(user_data)アクセス用リポジトリ */
	@Autowired
	private UserDataRepository repository;
	
	/**
	 * ユーザーデータリストを取得する.
	 * @return ユーザーデータリスト
	 */
	@GetMapping("/users")
	public List<UserData> getAllUserData() {
		return repository.findAll();
	}
	
	/**
	 * 指定したIDをもつユーザーデータを取得する.
	 * @param id ID
	 * @return 指定したIDをもつユーザーデータ
	 */
	@GetMapping("/users/{id}")
	public UserData getOneUserData(@PathVariable long id) {
		Optional<UserData> userData = repository.findById(id);
		// 指定したIDをもつユーザーデータがあればそのユーザーデータを返す
		if(userData.isPresent()) {
			return userData.get();
		}
		// 指定したIDをもつユーザーデータがなければnullを返す
		return null;
	}
	
	/**
	 * 指定したユーザーデータを登録・更新する.
	 * @param userData ユーザーデータ
	 * @return 登録・更新したユーザーデータ
	 */
	@PostMapping("/users")
	public UserData saveUserData(@RequestBody UserData userData) {
		// 本来は引数のチェック処理が必要であるが、ここでは実施していない
		return repository.save(userData);
	}
	
	/**
	 * 指定したIDをもつユーザーデータを削除する.
	 * @param id ID
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUserData(@PathVariable long id) {
		Optional<UserData> userData = repository.findById(id);
		// 指定したIDをもつユーザーデータがあればそのユーザーデータを削除する
		if(userData.isPresent()) {
			repository.deleteById(id);
		}
	}
}
