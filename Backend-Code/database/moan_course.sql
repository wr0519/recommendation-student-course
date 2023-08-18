/*
 Navicat Premium Data Transfer

 Source Server         : briup
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : moan_course

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 18/08/2023 22:58:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rc_admin
-- ----------------------------
DROP TABLE IF EXISTS `rc_admin`;
CREATE TABLE `rc_admin`  (
  `admin_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员Id',
  `admin_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `admin_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `admin_privilege` int NOT NULL COMMENT '角色\r\n二进制表示权限\r\n1-系管理\r\n2-专业管理\r\n4-班级管理\r\n8-学生管理\r\n16-教师管理\r\n32-课程管理\r\n64-选课管理\r\n128-管理员管理',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `idx_admin_username`(`admin_username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_admin
-- ----------------------------
INSERT INTO `rc_admin` VALUES (1, 'admin', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', 255);
INSERT INTO `rc_admin` VALUES (2, 'azure99', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', 96);

-- ----------------------------
-- Table structure for rc_class
-- ----------------------------
DROP TABLE IF EXISTS `rc_class`;
CREATE TABLE `rc_class`  (
  `class_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '班级Id',
  `class_major_id` int UNSIGNED NOT NULL COMMENT '专业Id',
  `class_grade` int UNSIGNED NOT NULL COMMENT '年级',
  `class_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  PRIMARY KEY (`class_id`) USING BTREE,
  INDEX `fk_major_id`(`class_major_id`) USING BTREE,
  INDEX `idx_class_name`(`class_name`) USING BTREE,
  CONSTRAINT `fk_major_id` FOREIGN KEY (`class_major_id`) REFERENCES `rc_major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_class
-- ----------------------------
INSERT INTO `rc_class` VALUES (24, 2, 2019, '软件1922');
INSERT INTO `rc_class` VALUES (25, 2, 2019, '软件1901');
INSERT INTO `rc_class` VALUES (26, 2, 2019, '软件1923');

-- ----------------------------
-- Table structure for rc_course
-- ----------------------------
DROP TABLE IF EXISTS `rc_course`;
CREATE TABLE `rc_course`  (
  `course_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '课程Id',
  `course_teacher_id` int UNSIGNED NOT NULL COMMENT '授课教师Id',
  `course_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `course_grade` int UNSIGNED NOT NULL COMMENT '授课年级',
  `course_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上课时间 星期几-第几节-几节课',
  `course_location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上课地址',
  `course_credit` int UNSIGNED NOT NULL COMMENT '学分',
  `course_selected_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '已选人数',
  `course_detail` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `course_max_size` int UNSIGNED NOT NULL COMMENT '最大容量',
  `course_exam_date` datetime NULL DEFAULT NULL COMMENT '考试时间',
  `course_exam_location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考试地点',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `fk_course_teacher_id`(`course_teacher_id`) USING BTREE,
  INDEX `idx_course_name`(`course_name`) USING BTREE,
  CONSTRAINT `fk_course_teacher_id` FOREIGN KEY (`course_teacher_id`) REFERENCES `rc_teacher` (`teacher_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_course
-- ----------------------------
INSERT INTO `rc_course` VALUES (1, 6, 'C语言程序设计', 2019, '1-1-2', '信工楼107', 5, 6, '《C语言程序设计》是软件工程专业学生必修的专业基础课程，是后续C++语言程序设计、数据结构、操作系统等课程的先修课程。  C语言是一门面向过程的计算机编程语言。C语言的设计目标是提供一种能以简易的方式编译、处理低级存储器、仅产生少量的机器码以及不需要任何运行环境支持便能运行的编程语言。  本课程的主要内容包括数据类型、数据运算、语句、函数、程序结构、数组、结构体、指针和文件。设置本课程的主要目的是培养学生的程序设计、实现及调试能力。  通过本课程的学习，使学生学会用计算机处理问题的思维方法，增强解决问题的编程实践能力，为将来从事软件开发及后继课程的学习和解决工程问题、科学技术问题奠定基础。', 50, '2023-04-25 00:00:00', 'test');
INSERT INTO `rc_course` VALUES (2, 1, 'Java程序设计', 2019, '1-3-2', '信工楼106', 4, 3, 'Java程序设计将内容归纳成9个单元，划分为3个层次。首先介绍Java基础语法（第一至第三单元），包括标识符与关键字、基本数据类型、数据输入与输出、数组、运算符、表达式和语句；其次讲解面向对象的封装、继承和多态特性在Java语言中的体现（第四和第五单元）；最后讲解Java实用程序设计及其API（第六至第九单元），包括异常处理、容器与泛型、多线程、流与文件等。', 30, NULL, NULL);
INSERT INTO `rc_course` VALUES (3, 1, '数据库实用技术', 2019, '2-3-2', 'C区202', 2, 4, '随着信息技术的不断发展，大学生吸收、处理、创造信息，以及组织、利用、规划资源的能力需求在不断提升。\n\n数据库技术是研究、管理和应用数据库的一门软件科学，是信息系统的一种核心技术，是进行组织和存储数据，高效地处理、分析和理解数据的技术，是进行数据库设计、数据存储、数据操纵与管理，以及数据库应用系统开发的基本理论方法。这门课程将让大家更好地理解什么是数据和数据库，以及系统的讲述数据库基础理论和基本操作。\n\n本课程力求通过培养学习者利用数据库技术对信息进行管理、加工和利用的“素养”，增强学习者分析问题和数据表达的能力；培养学习者利用数据库技术解决专业问题的“意识”，增强学习者根据应用问题选择、使用DBMS产品和应用开发工具的能力； 培养学习者积极探索新技术、新方法和继续学习的“理念”，增强学习者团队协作、自我创新的能力。让学习者感受信息文化、增强信息意识，养成利用信息技术解决问题的思维习惯，从而达到计算思维能力的培养的目标。\n\n本课程共有4个教学模块：数据库基础理论、数据库设计、数据操纵、数据库系统控制。课程的特色是采用“一托三”形式，以讲授数据库基础理论和基本操作为主，不局限某个数据库管理系统，提供ACCESS，SQL Server，Visual FoxPro三种不同实验环境的实验教学内容。学习者可根据实验条件进行针对性的选择，可以根据实验教程指导实验。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (4, 1, 'ASP.Net开发', 2019, '5-5-3', 'E区315', 2, 1, '\n\n“ASP.NET程序开发”是面向高职院校电子商务、计算机应用技术、信息管理等专业学生的一门专业课，该课程2学分。该课程以Smart商城商业项目为载体，从母板页制作、注册、登陆、商品展示、购物车、订单管理、商品管理、发布八个内容开展教学，将知识介绍和技能训练进行了有机结合。重点和难点主要包括ASP.NET验证控件、内置对象、ADO.NET数据访问、存储过程、DataList和GridView数据显示控件。\n', 1, NULL, NULL);
INSERT INTO `rc_course` VALUES (5, 1, 'Spring企业级开发', 2019, '3-9-2', '信工楼101', 3, 1, '“JavaEE平台技术”课程涵盖了三大部分的内容。第一部分是基于Spring框架构建服务端系统的基础知识，包括Servlet，Spring框架核心，SpringMVC，Spring AOP和Mybatis等内容。第二部分是构建高并发大负载系统所需的相关知识，包括Redis缓存、RocketMQ消息服务以及WebFlux等相关内容。第三部分是基于Spring Cloud Alibaba的微服务体系结构，这一部分主要介绍SpringCloud Gateway和Nacos。为了让同学能深入掌握此三部分内容，课程中包含了一个通用权限管理系统的完整设计和实现，涵盖了课程中的三部分所含的技术内容。通过本门课程的学习，学生不仅可以掌握开发高并发大负载应用系统的基本知识，也能了解工程化设计和开发软件方法。', 10, NULL, NULL);
INSERT INTO `rc_course` VALUES (6, 3, '数据库概论', 2019, '3-1-2', 'C区106', 5, 3, '“数据库系统概论”是计算机科学与技术专业、软件工程专业、信息系统与信息管理等专业重要的专业基础课程。\n\n本课程将系统讲述数据库系统的基础理论、基本技术和基本方法。本课程的知识内容和技术方法，对从事现代数据管理技术的应用、开发和研究的人员都是重要而必备的基础。\n\n本课程内容丰富全面，分为基础篇和高级篇（2023年2月24日开课）2部分讲解，作为2门课程考核和计分。此外，本课程还开设了新技术篇（2023年3月3日开课），从数据管理和数据分析的角度讨论数据库新技术与大数据技术。\n\n通过数据库系统概论（基础篇）的学习，学员可以系统地掌握数据库系统的基本原理，能熟练使用SQL语言在某一个数据库管理系统上进行数据库检索和操作，掌握数据库安全性和完整性的基本概念和基本方法。并能够在某一个数据库管理系统上进行实验。\n\n 通过数据库系统概论（高级篇）的学习，学员可以系统地掌握数据库规范化理论和数据库设计的方法与步骤，具有设计和开发数据库应用系统的基本能力；掌握数据库事务处理、并发控制与恢复的基本技术、初步掌握数据库查询处理和优化的概念。并能够在某一个数据库管理系统上进行实验验证。\n\n通过数据库系统概论（新技术篇）的学习，学员可以系统地掌握传统数据库技术的最新发展，大数据管理与系统的新概念、新技术和新应用。\n\n   本课程的特点是，理论联系实际。我们不仅希望学员通过阅读和书面习题掌握本课程的内容，还要求学员完成实验项目。为此我们针对课程知识点设置了相应的实验，锻炼学员实际动手能力，启发学员对理论知识的思考和理解，达到理论联系实际的教学效果。\n\n   本课程是教育部—华为“智能基座”精品课程之一。', 40, NULL, NULL);
INSERT INTO `rc_course` VALUES (7, 3, 'Photoshop', 2019, '2-3-2', 'C区222', 2, 0, 'Photoshop 是将科学技术设计和艺术设计紧密结合的优秀范例，它把计算机程序设计与平面设计有机地进行了组合，可以说是强强联合的设计手段的成果。当代美术类、设计类大学生学习好、运用好Photoshop,将会使自己的设计手段如虎添翼，设计过程有一日千里的感觉。\n\n本课程是教学团队在从事多年专业教学的基础上完成的。他们在多年的专业课教学中积累了丰富的课堂教学经验，积攒了大量的教学案例，他们通过深入浅出的阐述，从易到难、循序渐进地对软件进行了讲解。在此基础上，加强了实用案例的介绍，如标志设计应用、海报设计应用、环艺后期处理、动作动画设计、插画设计绘制等，通过经典案例教学的介绍，拓宽了Photoshop的艺术设计应用领域，对学生是一个很好的启发。本课程还特别重视对软件程序的开发潜能的介绍，尤其对软件底层设计逻辑的讲解，使学习该软件的学生更与时俱进。', 20, NULL, NULL);
INSERT INTO `rc_course` VALUES (8, 4, '计算机网络', 2019, '4-1-3', '信工楼109', 5, 1, '\n\n《计算机网络》课程分为三个单元：“计算机网络之网尽其用”、“计算机网络之探赜索隐”和“计算机网络之危机四伏”。\n\n“计算机网络之网尽其用”将带你快速了解、认识计算机网络，理解并掌握计算机网络与网络协议等基本概念、网络组成与网络体系结构，剖析你每天都在使用的网络应用的类型、运行原理以及应用层协议，帮助你理解绝大多数网络应用所采用的应用编程接口-套接字（Socket），学习并掌握Socket编程技术基础，具备开发简单网络应用的能力。\n\n“计算机网络之探赜索隐”将带你深入计算机网络内部，探究计算机网络深层奥秘，了解并掌握计算机网络深层次的原理、协议及网络技术，让你不仅知其然而且知其所以然，真正成为计算机网络的行家里手。这部分主要讲授：可靠数据传输基本原理、停-等协议与滑动窗口协议、典型传输层协议（UDP与TCP）、虚电路网络与数据报网络、路由与转发、IP协议与IP地址、CIDR、子网划分与路由聚集、ICMP协议、DHCP协议、NAT、IPv6、路由算法、路由协议、差错编码、MAC协议、ARP协议、以太网、VLAN、PPP协议、无线局域网等。\n\n“计算机网络之危机四伏”将带你一起认识网络安全威胁，理解并掌握保障网络安全的基本原理、网络协议以及技术措施，让你认识到如何在享受网络带给你诸多便利的同时尽可能避免令自身处于重重危机之中。这部分主要讲授：网络安全基本概念；网络安全威胁；密码学基础；信息完整性与数字签名；身份认证；安全电子邮件；SSL；IPsec与VPN；无线网局域网安全；防火墙；入侵检测等。\n', 20, NULL, NULL);
INSERT INTO `rc_course` VALUES (9, 1, '操作系统', 2019, '1-1-2', '行知楼', 2, 0, 'AIX是Advanced Interactive eXecutive的简称，它是IBM 公司的UNIX操作系统，整个系统的设计从网络、主机硬件系统，到操作系统完全遵守开放系统的原则. RS/6000 采用IBM 的UNIX操作系统-AIX作为其操作系统.这是一个操作系统界最成功，应用领域最广，最开放的第二代的UNIX系统。它特别适合于做关键数据处理（CRITICAL）. 支持PowerPC POWER处理器.', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (10, 4, ' 数理统计', 2019, '2-2-2', '行知楼', 2, 0, ' 人类社会已经开始进入大数据与人工智能时代。从大量数据中寻找客观规律，并加以利用，已经成为科技发展和社会进步的重要驱动力。\n\n      数理统计是一门以概率论为基础，运用统计的方法分析数据，进而研究大量随机现象中所蕴含规律的一门学科，是现代数学的重要组成部分。它的理论严谨，应用广泛，与实际问题结合紧密，在数据统计与分析方面发挥着重要的作用。\n\n     本课程共分为五个章节，内容包括：样本及统计量，总体分布中未知参数的估计，总体分布参数的假设检验，单因素试验的方差分析，一元线性回归等内容。\n     在教学设计上，采取了“导引+内容+小结”的设计，既能调动学生的学习兴趣，又能有效地帮助学生总结复习。在教学内容上，突出特色，既举了生动有趣的案例“女士品茶”，又举了很多农业方面的例子。在教学理念上，融入课程思政，介绍了我国古代的统计历史和思想，并且讲述了我国杰出的概率统计学家许宝騄先生报效祖国的事迹。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (11, 1, '基础法语', 2019, '2-2-2', '行勉楼A311', 2, 0, '《基础法语》是面向法语专业学生和法语爱好者的基础必修课，是整个教学体系中最重要、最基本的语言技能训练课程。从性质上看，本课程属于典型的语言教学，从基本发音开始，使学生掌握基础法语知识，具有听、说、读、写的基本技能和一定的交际能力，并具备初步的自学能力，为提高阶段的法语学习打下良好的基础。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (12, 1, '中国哲学', 2019, '2-2-2', '行远楼B412', 2, 0, '  本课程是高等院校本科哲学专业学生必修的专业基础课，旨在使学生获得基本而较系统的中国哲学史知识。通过本课程的教学，我们将让学生了解中国哲学的博大内涵，激发其哲学问题意识，提高其理论思维水平，掌握学习、研究中国哲学之方法，培养求实精神、严谨学风，引导学生追求高尚而健全的人格。\n\n\n        本课程以中国哲学的发展阶段为基础、学派流变为经线、问题论争为纬线，介绍中国哲学的代表人物、主要流派、经典著作及思想体系、重要命题、基本概念范畴，揭示其固有的逻辑发展进程，阐释其本体追求、思维方式、价值理想、人文关怀，彰显中国哲学的多元性、丰富性，为现代社会的文化建设、人文教养提供传统的精神资源。\n\n\n        本课程通过梳理中国哲学的演变过程，厘清其中的主要概念范畴，使学生领会中国哲学各个时代的继承与创新关系；消除时空的隔膜，使其能对中国古人的哲学思想产生一种切实的共鸣和感受，并理解哲学思想的时代性与哲学精神的恒久性，体悟到中国哲学仁智合一的特质，以之为自己个人的安身立命之所，积极成就个人、造福社会。\n\n\n        本课程还把教学生做学问与做人结合起来，着力发挥中国哲学仁智一体、知行合一的特色，引导学生追求健全的人格。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (13, 5, '交通设计', 2019, '2-2-2', '行知楼A412', 2, 0, '本课程重点讲述如下几部分内容：\n\n1. 交通设计概述。系统地定义与解释交通设计的基本概念，归纳提出交通设计的基本内容与作用，介绍交通设计在国内外的发展情况。\n\n2. 交通基础调查与分析。阐述主要交通问题的调查分析，以及影响交通问题的要素归纳分析方法，提出规划、设施设计及交通管理层面的交通设计问题与需求。\n\n3. 交通设计基础与条件。讨论交通设计条件及基础资料收集内容与方法，介绍交通设计需要参考的主要标准和规范。\n\n4. 城市道路交通系统设计方法。涉及道路网络衔接设计、交通流优化组织设计、道路横断面设计、交叉口无瓶颈化交通设计、连续流和间断流衔接设计、慢行交通系统设计、道路沿线进出交通设计、交叉口群协调设计等。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (14, 7, '金融数据挖掘（双语）', 2019, '2-2-2', '行远楼A313', 2, 0, '\n\n金融科技引领全球金融业新格局，在金融领域里，随着金融电子化建设的稳步开展，数据收集等技术的不断进步，金融数据资源得到了巨量增长，因此，为了充分利用数据,在最大程度上发挥出数据作用, 从而实现科学决策, 做好金融领域的数据挖掘工作是必不可少的，数据挖掘的先进技术和算法的产生也给实际金融问题的解决提供了新的解法和思路，由此进一步扩大了对数据挖掘人才的需求。本课程面向金融工程类、管理科学与工程类相关专业高年级本科生或低年级研究生。课程采用双语教学，紧跟数据科学前沿，并将数据挖掘算法与金融案例结合讲解，使得学生既能掌握数据挖掘算法的基本原理和核心思想，又能使学生理解算法在金融数据中的应用场景和计算流程。\n', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (15, 8, '导弹结构设计与分析', 2019, '2-2-2', '行逸楼A414', 2, 0, '课程的地位、作用和任务：\n\n本课程是军事航天工程领域导弹工程及相关专业的专业课程，较系统地阐述了导弹结构分析与设计的原理和方法，介绍了设计各阶段的步骤和准则，为军队和工业部门培养从事导弹结构设计与分析的后备队伍。\n\n\n教学目的：\n\n通过对导弹设计的基本概念、导弹的载荷分析、导弹结构分析、弹翼结构设计、弹身结构设计、操纵机构和分离机构的结构设计、导弹结构材料的选用等主要内容的学习，使学生对导弹结构的力学设计与分析方法有初步的了解，为学生从事导弹结构设计与分析工作奠定良好的基础', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (16, 1, 'Python语言程序设计', 2019, '2-2-2', '行知楼C313', 2, 0, '\n\n本课程适合如下教学目标：\n\n    程序设计入门课：面向各层次各专业大学在校生、部分优秀高中生，作为程序设计入门课程\n\n    体系化编程基础：面向拟构建坚实编程能力的自学者，作为不断奋斗的参考在线课程\n\n    Python科目备考：面向全国计算机等级考试二级Python科目的备考考生，作为在线备考资源\n\n    再试一次的尝试：面向拟放弃计算机或编程学习的学习者，作为再试一次的课程资源，学不会这门课学再放弃不迟...', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (17, 4, '国际私法学', 2019, '2-2-2', '行知楼B113', 2, 0, ' 从巴托鲁斯时代至今，国际私法之精神、理论已然经历了很大的变化。巴氏所谓人物两分的单边调整国际私法关系的方法几近消沉，即使是在其发源地的民法法系国家，自萨维尼以降，单边冲突规范也不再璀璨。放诸我国法律适用法，虽效大陆法系，但也深受美国冲突法革命之影响。条文规则多以多边冲突规范示人，采撷优法论、最密切联系原则等精华，仅有为数不多的单边冲突规范，也完全是出于维护本国利益、秩序之考量，极具政策性色彩。\n\n       寰宇各国交往日趋紧密，平等的民商事主体早已不拘一隅，故而构成了更为宏大且复杂的民商事法律关系网络。然，民商事关系本就繁杂，举凡有民法典之国家，无不耗精费神以求一部体例繁迭的私法典范。况且再添国际二字，诸国所定法律、主义不尽相同，复杂之上尤有麻烦。尽管各国协同，开辟了统一实体规范这一新的解决法律冲突的道路，但是体例有限，也不可能达成聚合诸国、涵摄诸事的统一条约，故单以此远不能自足。\n\n       是故，以调整国际间私法关系而致之诸多矛盾，于深耕于国内诸法的莘莘学子或显遥远，但实践之中日益增加的国际私法案件却让这门知识所载更重。如此一来，于我国而言，开放更甚，机会更甚，交流更甚，同时风险也更甚。《国际私法学》课程，即以此为鉴，顾法科门徒之所需，承司法实践之所要。夫有披沙拣金、尽巴托鲁斯以降之七百余年国际私法精要法门，但求能以绵薄之力启发后来之智；可望此学经久长延，以承时代发展之所需。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (18, 9, '声乐', 2019, '2-2-2', '行知楼A113', 2, 0, '本课程力求使歌唱更大众化、专业化，能更好地为学习者提供一个便利的、互动式的学习平台。学习者可以根据自身专业水平、兴趣点和条件有针对性的选择学习。通过线上学习、线下反思与实践，使学习者在获得一定的专业基础理论知识的同时，掌握扎实的演唱技能，对歌唱发声的基本原理和方法有较清楚的理解和认识，并能正确运用歌唱方法，解读歌曲内涵，把握不同风格的声乐作品，同时，也对民族戏曲声乐有着一定的认识。本课程内容将涵盖声乐基础理论知识、声乐基本技巧练习、中国声乐作品演唱教学、外国声乐作品演唱教学、声乐作品赏析、声乐舞台实践等方面。本课程期待在专家的指点下，美化您的声音，提高您的演唱水平。', 50, NULL, NULL);
INSERT INTO `rc_course` VALUES (19, 7, '形势与政策', 2019, '2-2-2', '行知楼A124', 2, 0, '紧密结合实际，针对学生关注的热点问题和思想特点，引导学生正确认识世界和中国发展大势、正确认识中国特色和国际比较、正确认识时代责任和历史使命、正确认识远大抱负和脚踏实地，增强了学生对形势与政策课的“获得感”', 50, NULL, NULL);

-- ----------------------------
-- Table structure for rc_department
-- ----------------------------
DROP TABLE IF EXISTS `rc_department`;
CREATE TABLE `rc_department`  (
  `department_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '系Id',
  `department_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系名称',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_department
-- ----------------------------
INSERT INTO `rc_department` VALUES (1, '软件工程系');
INSERT INTO `rc_department` VALUES (2, '数学系');
INSERT INTO `rc_department` VALUES (3, '物理系');
INSERT INTO `rc_department` VALUES (4, '化学系');
INSERT INTO `rc_department` VALUES (5, '管理系');
INSERT INTO `rc_department` VALUES (6, '外国语系');

-- ----------------------------
-- Table structure for rc_major
-- ----------------------------
DROP TABLE IF EXISTS `rc_major`;
CREATE TABLE `rc_major`  (
  `major_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '专业Id',
  `major_department_id` int UNSIGNED NOT NULL COMMENT '系Id',
  `major_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专业名称',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `fk_major_department_id`(`major_department_id`) USING BTREE,
  INDEX `idx_major_name`(`major_name`) USING BTREE,
  CONSTRAINT `fk_major_department_id` FOREIGN KEY (`major_department_id`) REFERENCES `rc_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_major
-- ----------------------------
INSERT INTO `rc_major` VALUES (2, 1, '软件工程');

-- ----------------------------
-- Table structure for rc_recommend
-- ----------------------------
DROP TABLE IF EXISTS `rc_recommend`;
CREATE TABLE `rc_recommend`  (
  `student_id` int UNSIGNED NOT NULL COMMENT '学生Id',
  `course_id` int UNSIGNED NOT NULL,
  PRIMARY KEY (`student_id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `rc_course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `rc_student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_recommend
-- ----------------------------
INSERT INTO `rc_recommend` VALUES (1, 3);
INSERT INTO `rc_recommend` VALUES (2, 5);
INSERT INTO `rc_recommend` VALUES (3, 5);
INSERT INTO `rc_recommend` VALUES (2, 6);
INSERT INTO `rc_recommend` VALUES (1, 7);
INSERT INTO `rc_recommend` VALUES (3, 7);
INSERT INTO `rc_recommend` VALUES (4, 7);
INSERT INTO `rc_recommend` VALUES (3, 8);
INSERT INTO `rc_recommend` VALUES (4, 8);
INSERT INTO `rc_recommend` VALUES (2, 9);
INSERT INTO `rc_recommend` VALUES (3, 9);
INSERT INTO `rc_recommend` VALUES (4, 9);
INSERT INTO `rc_recommend` VALUES (1, 14);
INSERT INTO `rc_recommend` VALUES (2, 14);
INSERT INTO `rc_recommend` VALUES (3, 14);
INSERT INTO `rc_recommend` VALUES (4, 14);
INSERT INTO `rc_recommend` VALUES (1, 16);
INSERT INTO `rc_recommend` VALUES (2, 16);
INSERT INTO `rc_recommend` VALUES (3, 16);
INSERT INTO `rc_recommend` VALUES (4, 16);
INSERT INTO `rc_recommend` VALUES (1, 19);
INSERT INTO `rc_recommend` VALUES (2, 19);
INSERT INTO `rc_recommend` VALUES (3, 19);
INSERT INTO `rc_recommend` VALUES (4, 19);

-- ----------------------------
-- Table structure for rc_student
-- ----------------------------
DROP TABLE IF EXISTS `rc_student`;
CREATE TABLE `rc_student`  (
  `student_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '学生Id',
  `student_class_id` int UNSIGNED NOT NULL COMMENT '班级Id',
  `student_number` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学号',
  `student_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `student_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `student_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `student_birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `student_sex` tinyint UNSIGNED NOT NULL COMMENT '性别',
  `student_last_login_time` datetime NULL DEFAULT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `idx_student_number`(`student_number`) USING BTREE,
  INDEX `fk_student_class_id`(`student_class_id`) USING BTREE,
  INDEX `idx_student_name`(`student_name`) USING BTREE,
  CONSTRAINT `fk_student_class_id` FOREIGN KEY (`student_class_id`) REFERENCES `rc_class` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_student
-- ----------------------------
INSERT INTO `rc_student` VALUES (1, 24, '2019005982', '王瑞', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', '2303112308@qq.com', '2000-06-21 00:00:00', 1, '2023-06-03 14:52:48');
INSERT INTO `rc_student` VALUES (2, 24, '2019005983', '赵鹏飞', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, '2023-04-07 00:00:00', 1, '2023-05-16 11:28:36');
INSERT INTO `rc_student` VALUES (3, 24, '2019005984', '张智慧', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, '2023-05-06 19:15:54');
INSERT INTO `rc_student` VALUES (4, 24, '2019005985', '尹异凡', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, '2023-05-06 19:21:42');
INSERT INTO `rc_student` VALUES (5, 24, '2019005986', '李佳辉', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, '2023-05-06 11:42:30');
INSERT INTO `rc_student` VALUES (6, 24, '2019005987', '王一', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', NULL, NULL, 1, NULL);
INSERT INTO `rc_student` VALUES (7, 24, '2019005981', '王哲', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', '2303112308@qq.com', '2023-04-24 00:00:00', 1, '2023-04-07 18:54:03');
INSERT INTO `rc_student` VALUES (36, 24, '2019005978', '马海', '81a5f5a9bfde4cdcb5b9fe1f8508df2a', '1609628369@qq.com', '2000-05-05 00:00:00', 1, '2023-05-22 12:07:49');

-- ----------------------------
-- Table structure for rc_student_course
-- ----------------------------
DROP TABLE IF EXISTS `rc_student_course`;
CREATE TABLE `rc_student_course`  (
  `sc_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '选课Id',
  `sc_student_id` int UNSIGNED NOT NULL COMMENT '学生Id',
  `sc_course_id` int UNSIGNED NOT NULL COMMENT '课程Id',
  `sc_daily_score` int UNSIGNED NULL DEFAULT NULL COMMENT '日常表现分',
  `sc_exam_score` int UNSIGNED NULL DEFAULT NULL COMMENT '期末测试分',
  `sc_score` int UNSIGNED NULL DEFAULT NULL COMMENT '总成绩',
  PRIMARY KEY (`sc_id`) USING BTREE,
  INDEX `fk_sc_course_id`(`sc_course_id`) USING BTREE,
  INDEX `fk_sc_student_id`(`sc_student_id`) USING BTREE,
  CONSTRAINT `fk_sc_course_id` FOREIGN KEY (`sc_course_id`) REFERENCES `rc_course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_sc_student_id` FOREIGN KEY (`sc_student_id`) REFERENCES `rc_student` (`student_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_student_course
-- ----------------------------
INSERT INTO `rc_student_course` VALUES (19, 1, 3, 99, 100, 99);
INSERT INTO `rc_student_course` VALUES (27, 1, 1, 98, 98, 98);
INSERT INTO `rc_student_course` VALUES (29, 1, 2, 80, 80, 80);
INSERT INTO `rc_student_course` VALUES (31, 2, 8, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (32, 2, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (33, 3, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (34, 4, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (35, 5, 1, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (36, 1, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (37, 2, 3, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (38, 3, 2, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (39, 4, 3, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (42, 3, 3, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (44, 3, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (45, 3, 4, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (46, 4, 6, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (47, 4, 2, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (48, 4, 5, NULL, NULL, NULL);
INSERT INTO `rc_student_course` VALUES (50, 36, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rc_teacher
-- ----------------------------
DROP TABLE IF EXISTS `rc_teacher`;
CREATE TABLE `rc_teacher`  (
  `teacher_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教师Id',
  `teacher_department_id` int UNSIGNED NOT NULL COMMENT '系Id',
  `teacher_number` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `teacher_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教师姓名',
  `teacher_password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`teacher_id`) USING BTREE,
  UNIQUE INDEX `idx_teacher_number`(`teacher_number`) USING BTREE,
  INDEX `fk_teacher_department_id`(`teacher_department_id`) USING BTREE,
  CONSTRAINT `fk_teacher_department_id` FOREIGN KEY (`teacher_department_id`) REFERENCES `rc_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rc_teacher
-- ----------------------------
INSERT INTO `rc_teacher` VALUES (1, 1, '2019005982', '王老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (3, 1, '2019005983', '宋老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (4, 1, '2019005984', '张老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (5, 1, '2019005985', '吕老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (6, 1, '2019005986', '王老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (7, 1, '2019005987', '丁老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (8, 1, '2019005988', '高老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');
INSERT INTO `rc_teacher` VALUES (9, 1, '2019005989', '赵老师', '81a5f5a9bfde4cdcb5b9fe1f8508df2a');

SET FOREIGN_KEY_CHECKS = 1;
