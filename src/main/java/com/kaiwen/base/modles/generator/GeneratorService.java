package com.kaiwen.base.modles.generator;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author: liangjinyin
 * @Date: 2018-12-11
 * @Description:
 */
public class GeneratorService {

    public static void printService(String name,FileWriter fw) throws IOException{
        fw.write("import lombok.extern.slf4j.Slf4j;\n" +
                "import org.apache.commons.lang3.StringUtils;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import javax.persistence.criteria.*;\n" +
                "import org.springframework.data.domain.*;\n" +
                "import org.springframework.lang.Nullable;\n" +
                "import org.springframework.data.jpa.domain.Specification;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n");

        fw.write("/**\n" +
                " * @author: liangjinyin\n" +
                " * @Date: "+ DateFormatUtils.format(new Date(),"yyyy-MM-dd")+"\n" +
                " * @Description:\n" +
                " */\n");

        fw.write("@Service\n" + "@Slf4j\n");
        fw.write("public class "+name+"Service {");
        fw.write("\n");
        fw.write("    @Autowired\n" + "    private "+name+"Repository "+name.toLowerCase()+"Repository;");
        fw.write("\n");
        fw.write("    public Page<"+name+"> findPage(MyPage page, String key) {\n" +
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
            
        
        fw.write("    public Object find"+name+"ById(Integer id) {\n" +
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
        fw.write("}");
    }
}
