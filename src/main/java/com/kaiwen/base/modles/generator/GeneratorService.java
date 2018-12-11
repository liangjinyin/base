package com.kaiwen.base.modles.generator;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorService {

    public static void printService(String name) {
        System.out.println("@Service\n" + "@Slf4j");
        System.out.print("public class "+name+"Service {");
        System.out.println();
        System.out.print("    @Autowired\n" + "    private "+name+"Repository "+name.toLowerCase()+"Repository;");
        System.out.println();
        System.out.print("    public Page<"+name+"> findPage(MyPage page, String key) {\n" +
                "        try {\n" +
                "            Specification specification = new Specification<"+name+">() {\n" +
                "                @Nullable\n" +
                "                @Override\n" +
                "                public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {\n" +
                "                    List<Predicate> predicates = new ArrayList<>(8);\n" +
                "                    if (StringUtils.isNotBlank(key)){\n" +
                "                        Predicate likeNickName = cb.like(root.get(\"operator\").as(String.class), \"%\"+key+\"%\");\n" +
                "                        predicates.add(likeNickName);\n" +
                "                    }\n" +
                "                    return cb.and(predicates.toArray(new Predicate[0]));\n" +
                "                }\n" +
                "            };\n" +
                "            Sort sort = new Sort(Sort.Direction.DESC, \"updateTime\");\n" +
                "            Pageable pageable = PageRequest.of(page.getPage() - 1, page.getSize(),sort);\n" +
                "            Page all = "+name.toLowerCase()+"Repository.findAll(specification, pageable);\n" +
                "            return all;\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            log.error(\"{} 类出现了异常，异常信息为：{}\",getClass().getSimpleName(),e.getMessage());\n" +
                "            return null;\n" +
                "        }\n" );
            
        
        System.out.print("    public Object find"+name+"ById(Integer id) {\n" +
                "        try {\n" +
                "            "+name+" "+name.toLowerCase()+" = "+name.toLowerCase()+"Repository.findById(id).get();\n" +
                "            return "+name.toLowerCase()+";\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            log.error(\"{} 类出现了异常，异常信息为：{}\", getClass().getSimpleName(), e.getMessage());\n" +
                "            return ResultCode.OPERATION_FAILED;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @Transactional(rollbackFor = Exception.class)\n" +
                "    public Object save"+name+"("+name+" "+name.toLowerCase()+") {\n" +
                "        try {\n" +
                "            return "+name.toLowerCase()+"Repository.save("+name.toLowerCase()+");\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            log.error(\"{} 类出现了异常，异常信息为：{}\", getClass().getSimpleName(), e.getMessage());\n" +
                "            return ResultCode.OPERATION_FAILED;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @Transactional(rollbackFor = Exception.class)\n" +
                "    public Object delete"+name+"ById(Integer id) {\n" +
                "        try {\n" +
                "            if ("+name.toLowerCase()+"Repository.existsById(id)) {\n" +
                "                "+name.toLowerCase()+"Repository.deleteById(id);\n" +
                "            } else {\n" +
                "                return ResultCode.ENTITY_NOT_EXIST;\n" +
                "            }\n" +
                "            return null;\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            log.error(\"{} 类出现了异常，异常信息为：{}\", getClass().getSimpleName(), e.getMessage());\n" +
                "            return ResultCode.OPERATION_FAILED;\n" +
                "        }\n" +
                "    }\n");
        System.out.print("}");
    }
}
