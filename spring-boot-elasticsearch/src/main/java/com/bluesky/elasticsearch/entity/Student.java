/*
 * Copyright (c) 2022, wangguodong194@163.com. All rights reserved.
 */

package com.bluesky.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author blue Sky
 * @version V1.0
 * @description
 * @date 2022-11-24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
public class Student {

	private String name;

	private String school;
}
